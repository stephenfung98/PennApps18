package sfung.buffalo.edu.myapplication;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.models.nosql.LyftPricesDO;
import com.amazonaws.models.nosql.UberPricesDO;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import sfung.buffalo.edu.myapplication.Modules.DirectionFinder;
import sfung.buffalo.edu.myapplication.Modules.DirectionFinderListener;
import sfung.buffalo.edu.myapplication.Modules.Route;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback, DirectionFinderListener {

    private GoogleMap mMap;
    LocationManager locationManager;
    PlaceAutocompleteFragment placeAutoCompleteFrom;
    PlaceAutocompleteFragment placeAutoCompleteTo;
    private Marker markerTo;
    private Marker markerFrom;
    LatLng latlngTo;
    LatLng latlngFrom;
    boolean bootLocation = false;
    private String pickUp;
    private String destination;
    private double duration;
    private double distance;
    static String city;
    DynamoDBMapper dynamoDBMapper;
    LyftPricesDO lyftPricesDO;
    UberPricesDO uberPricesDO;

    //    static double lyftLinePrice = 0.0;
    static double lyftPrice = 0.0;
    static double lyftPlusPrice = 0.0;
    static double lyftPremierPrice = 0.0;
    static double lyftLuxPrice = 0.0;
    static double lyftLuxSUVPrice = 0.0;

    //    static double uberPoolPrice = 0.0;
    static double uberXPrice = 0.0;
    static double uberXLPrice = 0.0;
    static double uberSelectPrice = 0.0;
    static double uberBlackPrice = 0.0;
    static double uberSUVPrice = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //establish a connection with AWS Mobile
        AWSMobileClient.getInstance().initialize(this).execute();
        //DynamoDBMapper client
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();

        setContentView(R.layout.activity_maps);
        //Gets location
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                //on first boot get location and puts it in the textview
                @Override
                public void onLocationChanged(Location location) {
                    //If it is the first boot, get lat, lng, city, add marker, then get price for the city from AWS
                    if (!bootLocation) {
                        bootLocation = true;
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        latlngFrom = new LatLng(latitude, longitude);
                        Geocoder geocoder = new Geocoder(getApplicationContext());
                        //gets city from geocoder
                        try {
                            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 10);
                            if (addresses != null && addresses.size() > 0) {
                                for (Address adr : addresses) {
                                    if (adr.getLocality() != null && adr.getLocality().length() > 0) {
                                        city = adr.getLocality();
                                        break;
                                    }
                                }
                            }
                            String str = null;
                            if (addresses != null) {
                                str = addresses.get(0).getAddressLine(0);
                            }
                            pickUp = str;
                            placeAutoCompleteFrom = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.from_autocomplete);
                            placeAutoCompleteFrom.setText(str);
                            markerFrom = mMap.addMarker(new MarkerOptions().position(latlngFrom).title(str));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngFrom, 15.2f));
                            //thread to get price of current city at boot
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    lyftPricesDO = dynamoDBMapper.load(LyftPricesDO.class, city);
                                    uberPricesDO = dynamoDBMapper.load(UberPricesDO.class, city);
                                }
                            }).start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {}

                @Override
                public void onProviderEnabled(String provider) {}

                @Override
                public void onProviderDisabled(String provider) {}

            });
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //If it is the first boot, get lat, lng, city, add marker, then get price for the city from AWS
                    if (!bootLocation) {
                        bootLocation = true;
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        latlngFrom = new LatLng(latitude, longitude);
                        Geocoder geocoder = new Geocoder(getApplicationContext());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 10);
                            if (addresses != null && addresses.size() > 0) {
                                for (Address adr : addresses) {
                                    if (adr.getLocality() != null && adr.getLocality().length() > 0) {
                                        city = adr.getLocality();
                                        break;
                                    }
                                }
                            }
                            String str = null;
                            if (addresses != null) {
                                str = addresses.get(0).getAddressLine(0);
                            }
                            pickUp = str;
                            placeAutoCompleteFrom = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.from_autocomplete);
                            placeAutoCompleteFrom.setText(str);
                            markerFrom = mMap.addMarker(new MarkerOptions().position(latlngFrom).title(str));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngFrom, 15.2f));
                            //thread to get price of current city at boot
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    lyftPricesDO = dynamoDBMapper.load(LyftPricesDO.class, city);
                                    uberPricesDO = dynamoDBMapper.load(UberPricesDO.class, city);
                                }
                            }).start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {}

                @Override
                public void onProviderEnabled(String provider) {}

                @Override
                public void onProviderDisabled(String provider) {}

            });
        }
        //changes font for fairRide
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "poiret_one.ttf");
        TextView myTextView = findViewById(R.id.titleTextView);
        myTextView.setTypeface(myTypeface);
        //compare price button
        Button button = findViewById(R.id.ComparePrice);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent priceIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(priceIntent);
            }
        });


        //FROM AUTOCOMPLETE
        placeAutoCompleteFrom = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.from_autocomplete);
        ((EditText) placeAutoCompleteFrom.getView().findViewById(R.id.place_autocomplete_search_input)).setTextSize(12.0f);
        ((EditText) placeAutoCompleteFrom.getView().findViewById(R.id.place_autocomplete_search_input)).setTextColor(Color.WHITE);
        placeAutoCompleteFrom.setHint("Enter your pickup location");
        placeAutoCompleteFrom = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.from_autocomplete);
        placeAutoCompleteFrom.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                if (markerFrom == null) {
                    markerFrom = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getAddress().toString()));
                    latlngFrom = place.getLatLng();
                    pickUp = place.getAddress().toString();
                } else {
                    markerFrom.remove();
                    markerFrom = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getAddress().toString()));
                    latlngFrom = place.getLatLng();
                    pickUp = place.getAddress().toString();
                    Geocoder gcd = new Geocoder(getApplicationContext());
                    List<Address> addresses = null;
                    try {
                        addresses = gcd.getFromLocation(latlngFrom.latitude, latlngFrom.longitude, 10);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (addresses != null && addresses.size() > 0) {
                        for (Address adr : addresses) {
                            if (adr.getLocality() != null && adr.getLocality().length() > 0) {
                                city = adr.getLocality();
                            }
                        }
                    }
                    //thread to get price of when from location is changed
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            lyftPricesDO = dynamoDBMapper.load(LyftPricesDO.class, city);
                            uberPricesDO = dynamoDBMapper.load(UberPricesDO.class, city);
                        }
                    }).start();
                    //Gets distance and duration
                    if (markerTo != null && markerFrom != null) {
                        try {
                            new DirectionFinder(MapsActivity.this, pickUp, destination).execute();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //if there is a to location, add to and from to LatLngBounds and update camera to show both, else just show the from location
                if (latlngTo != null) {
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    builder.include(place.getLatLng());
                    builder.include(latlngTo);
                    LatLngBounds bounds = builder.build();
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 150);
                    mMap.animateCamera(cameraUpdate);
                } else {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngFrom, 15.2f));
                }
            }

            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //TO AUTOCOMPLETE
        placeAutoCompleteTo = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.to_autocomplete);
        ((EditText) placeAutoCompleteTo.getView().findViewById(R.id.place_autocomplete_search_input)).setTextSize(12.0f);
        ((EditText) placeAutoCompleteTo.getView().findViewById(R.id.place_autocomplete_search_input)).setTextColor(Color.WHITE);
        placeAutoCompleteTo.setHint("Enter your destination");
        placeAutoCompleteTo.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // if there is no to marker
                if (markerTo == null) {
                    markerTo = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getAddress().toString()));
                    latlngTo = place.getLatLng();
                    destination = place.getAddress().toString();
                    //calculates distance and duration from two points
                    if (markerTo != null && markerFrom != null) {
                        try {
                            new DirectionFinder(MapsActivity.this, pickUp, destination).execute();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    markerTo.remove();
                    markerTo = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getAddress().toString()));
                    latlngTo = place.getLatLng();
                    destination = place.getAddress().toString();
                    Geocoder gcd = new Geocoder(getApplicationContext());
                    List<Address> addresses = null;
                    try {
                        addresses = gcd.getFromLocation(latlngFrom.latitude, latlngFrom.longitude, 10);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (addresses != null && addresses.size() > 0) {
                        for (Address adr : addresses) {
                            if (adr.getLocality() != null && adr.getLocality().length() > 0) {
                                city = adr.getLocality();
                                break;
                            }
                        }
                    }
                    //thread to get price when to city is changed
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            lyftPricesDO = dynamoDBMapper.load(LyftPricesDO.class, city);
                            uberPricesDO = dynamoDBMapper.load(UberPricesDO.class, city);
                        }
                    }).start();
                    //Gets distance and duration
                    if (markerTo != null && markerFrom != null) {
                        try {
                            new DirectionFinder(MapsActivity.this, pickUp, destination).execute();

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //to update zoom based on to/from markers
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(place.getLatLng());
                if (latlngFrom != null) {
                    builder.include(latlngFrom);
                }
                LatLngBounds bounds = builder.build();
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 150);
                mMap.animateCamera(cameraUpdate);
            }

            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will ob nly be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }


    @Override
    public void onDirectionFinderStart() {
    }

    //once direction finder is successful, parse the duration and minutes. After run 2 threads that ping the server for lyft and uber prices
    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        for (Route route : routes) {
            double x;
            String dis;
            if (route.distance.text.length() > 7) {
                dis = route.distance.text.substring(0, 1);
                dis += route.distance.text.substring(2, route.distance.text.length() - 3);
            } else {
                dis = route.distance.text.substring(0, route.distance.text.length() - 3);
            }
            if (route.duration.text.charAt(2) == 'm') {
                x = Integer.parseInt(route.duration.text.substring(0, 1));
            } else if (route.duration.text.charAt(3) == 'h') {
                x = Integer.parseInt(route.duration.text.substring(0, 1));
                dis += route.distance.text.substring(2, route.distance.text.length() - 3);
            } else if (route.duration.text.charAt(3) == 'm') {
                x = Integer.parseInt(route.duration.text.substring(0, route.duration.text.length() - 5));
            } else if (route.duration.text.charAt(2) == 'd') {
                x = Integer.parseInt(route.duration.text.substring(0, 1)) * 1440;
                x += Integer.parseInt(route.duration.text.substring(route.duration.text.length() - 8, route.duration.text.length() - 6)) * 60;
            } else {
                x = Integer.parseInt(route.duration.text.substring(8, 9));
                x += Integer.parseInt(route.duration.text.substring(0, 1)) * 60;
            }
            distance = Double.parseDouble(dis);
            duration = x;

            //thread for lyft
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
//                    lyftLinePrice = (dynamoDBMapper.load(LyftPricesDO.class, city).getBaseLine() + dynamoDBMapper.load(LyftPricesDO.class, city).getMinuteLine() * duration + dynamoDBMapper.load(LyftPricesDO.class, city).getMileLine() * distance) * dynamoDBMapper.load(LyftPricesDO.class, city).getTaxAndFees();
                        lyftPrice = (lyftPricesDO.getBaseLyft() + lyftPricesDO.getMinuteLyft() * duration + lyftPricesDO.getMileLyft() * distance) * lyftPricesDO.getTaxAndFees();
                        lyftPlusPrice = (lyftPricesDO.getBasePlus() + lyftPricesDO.getMinutePlus() * duration + lyftPricesDO.getMilePlus() * distance) * lyftPricesDO.getTaxAndFees();
                        lyftPremierPrice = (lyftPricesDO.getBasePremier() + lyftPricesDO.getMinutePremier() * duration + lyftPricesDO.getMilePremier() * distance) * lyftPricesDO.getTaxAndFees();
                        lyftLuxPrice = (lyftPricesDO.getBaseLux() + lyftPricesDO.getMinuteLux() * duration + lyftPricesDO.getMileLux() * distance) * lyftPricesDO.getTaxAndFees();
                        lyftLuxSUVPrice = (lyftPricesDO.getBaseLuxSUV() + lyftPricesDO.getMinuteLuxSUV() * duration + lyftPricesDO.getMileLuxSUV() * distance) * lyftPricesDO.getTaxAndFees();
                    } catch (NullPointerException e) {
                        lyftPrice = 0;
                        lyftPlusPrice = 0;
                        lyftPremierPrice = 0;
                        lyftLuxPrice = 0;
                        lyftLuxSUVPrice = 0;
                    }
                }
            }).start();

            //thread for uber
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
//                    uberPoolPrice = (dynamoDBMapper.load(UberPricesDO.class, city).getBasePool() + dynamoDBMapper.load(UberPricesDO.class, city).getMinutePool() * duration + dynamoDBMapper.load(UberPricesDO.class, city).getMilePool() * distance) * dynamoDBMapper.load(UberPricesDO.class, city).getTaxAndFees();
                        uberXPrice = (uberPricesDO.getBaseX() + uberPricesDO.getMinuteX() * duration + uberPricesDO.getMileX() * distance) * uberPricesDO.getTaxAndFees();
                        uberXLPrice = (uberPricesDO.getBaseXL() + uberPricesDO.getMinuteXL() * duration + uberPricesDO.getMileXL() * distance) * uberPricesDO.getTaxAndFees();
                        uberSelectPrice = (uberPricesDO.getBaseSelect() + uberPricesDO.getMinuteSelect() * duration + uberPricesDO.getMileSelect() * distance) * uberPricesDO.getTaxAndFees();
                        uberBlackPrice = (uberPricesDO.getBaseBlack() + uberPricesDO.getMinuteBlack() * duration + uberPricesDO.getMileBlack() * distance) * uberPricesDO.getTaxAndFees();
                        uberSUVPrice = (uberPricesDO.getBaseSUV() + uberPricesDO.getMinuteSUV() * duration + uberPricesDO.getMileSUV() * distance) * uberPricesDO.getTaxAndFees();
                    } catch (NullPointerException e) {
                        uberXPrice = 0;
                        uberXLPrice = 0;
                        uberSelectPrice = 0;
                        uberBlackPrice = 0;
                        uberSUVPrice = 0;
                    }
                }
            }).start();
        }
    }
}

