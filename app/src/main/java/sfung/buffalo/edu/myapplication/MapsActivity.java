package sfung.buffalo.edu.myapplication;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import sfung.buffalo.edu.myapplication.Modules.DirectionFinder;
import sfung.buffalo.edu.myapplication.Modules.DirectionFinderListener;
import sfung.buffalo.edu.myapplication.Modules.Route;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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
    private String destinationn;
    private double duration;
    private double distance;
    private String state;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Button button = (Button) findViewById(R.id.ComparePrice);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
//                sendRequest();
            }
        });

        //GETS LOCATION
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},200);
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {


            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (!bootLocation) {
                        bootLocation = true;
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        latlngFrom = new LatLng(latitude, longitude);
                        Geocoder geocoder = new Geocoder(getApplicationContext());

                        try {
                            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                            String str = addressList.get(0).getAddressLine(0);
                            state = addressList.get(0).getAdminArea();
                            pickUp = str;
                            placeAutoCompleteFrom = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.from_autocomplete);
                            String value = str;
                            placeAutoCompleteFrom.setText(value);



                            markerFrom = mMap.addMarker(new MarkerOptions().position(latlngFrom).title(str));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngFrom, 15.2f));


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LatLng latlng = new LatLng(latitude, longitude);
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);

                        String str = addressList.get(0).getAddressLine(0);
                        placeAutoCompleteFrom = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.from_autocomplete);
                        String value = str;
                        placeAutoCompleteFrom.setText(value);
                        if (markerFrom == null) {
                            markerFrom = mMap.addMarker(new MarkerOptions().position(latlng).title(str));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15.2f));
                            pickUp = value;
                        } else {
                            markerFrom.remove();
                            markerFrom = mMap.addMarker(new MarkerOptions().position(latlng).title(str));
                            pickUp = value;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
//FROM AUTOCOMPLETE
        placeAutoCompleteFrom = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.from_autocomplete);
        ((EditText) placeAutoCompleteFrom.getView().findViewById(R.id.place_autocomplete_search_input)).setTextSize(12.0f);
        placeAutoCompleteFrom.setHint("Enter your pickup location");

        placeAutoCompleteFrom = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.from_autocomplete);
        placeAutoCompleteFrom.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                if (markerFrom == null) {
                    markerFrom = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getAddress().toString()));
                    latlngFrom = place.getLatLng();
                    destinationn = place.getAddress().toString();
                } else {
                    markerFrom.remove();
                    markerFrom = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getAddress().toString()));
                    latlngFrom = place.getLatLng();
                    destinationn = place.getAddress().toString();
                    Geocoder gcd = new Geocoder(getApplicationContext());
                    List<Address> addresses = null;
                    try {
                        addresses = gcd.getFromLocation(latlngFrom.latitude, latlngFrom.longitude, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (addresses != null && addresses.size() > 0) {
                        state = addresses.get(0).getAdminArea();
                    }

                }
                if(latlngTo != null) {
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    // Add your locations to bounds using builder.include, maybe in a loop
                    builder.include(place.getLatLng());

                    builder.include(latlngTo);
                    LatLngBounds bounds = builder.build();

                    //Then construct a cameraUpdate
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 150);
                    //Then move the camera
                    mMap.animateCamera(cameraUpdate);
                }
                else{
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngFrom, 15.2f));
                }
            }

            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });


//IDK
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);





//TO AUTOCOMPLETE
        placeAutoCompleteTo = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.to_autocomplete);
        ((EditText) placeAutoCompleteTo.getView().findViewById(R.id.place_autocomplete_search_input)).setTextSize(12.0f);
        placeAutoCompleteTo.setHint("Enter your destination");
        placeAutoCompleteTo.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                if (markerTo == null) {
                    markerTo = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getAddress().toString()));
                    latlngTo = place.getLatLng();
                    pickUp = place.getAddress().toString();

                } else {
                    markerTo.remove();
                    markerTo = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getAddress().toString()));
                    latlngTo = place.getLatLng();
                    pickUp = place.getAddress().toString();
                    Geocoder gcd = new Geocoder(getApplicationContext());
                    List<Address> addresses = null;
                    try {
                        addresses = gcd.getFromLocation(latlngFrom.latitude, latlngFrom.longitude, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (addresses != null && addresses.size() > 0) {
                        state = addresses.get(0).getAdminArea();
                    }

                }
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                // Add your locations to bounds using builder.include, maybe in a loop
                builder.include(place.getLatLng());
                if (latlngFrom != null) {
                    builder.include(latlngFrom);
                }
                LatLngBounds bounds = builder.build();

                //Then construct a cameraUpdate
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 150);
                //Then move the camera
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 200: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // {Some Code}
                }
            }
        }
    }





    private void sendRequest() {
        String origin = pickUp;
        String destination = destinationn;
        if (markerTo == null) {
            Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (markerFrom == null) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinder(this, origin, destination).execute();
            Intent startIntent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(startIntent);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDirectionFinderStart() {

    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        for (Route route : routes) {
            double x=0;
            String dis = route.distance.text.substring(0, route.distance.text.length()-3);
            if(route.duration.text.charAt(2) != 'h') {
                x = Integer.parseInt(route.duration.text.substring(0, route.duration.text.length() - 5));
            }
            else{
                x =  Integer.parseInt(route.duration.text.substring(7, 9));
                x = x + Integer.parseInt(route.duration.text.substring(0, 1)) * 60;

            }

            distance = Double.parseDouble(dis);

            duration = x;

//            placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.to_autocomplete);
//
//            placeAutoComplete.setText(state);
//            placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.from_autocomplete);
//
//            placeAutoComplete.setText(Double.toString(duration));


        }
    }
}