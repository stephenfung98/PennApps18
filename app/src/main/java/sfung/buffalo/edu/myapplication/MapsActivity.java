package sfung.buffalo.edu.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.EditText;
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
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    PlaceAutocompleteFragment placeAutoComplete;
    PlaceAutocompleteFragment placeAutoCompleteTo;
    private Marker markerTo;
    private Marker markerFrom;
    LatLng latlngTo;
    LatLng latlngFrom;
    boolean bootLocation = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.to_autocomplete);
        ((EditText)placeAutoComplete.getView().findViewById(R.id.place_autocomplete_search_input)).setTextSize(12.0f);
        placeAutoComplete.setHint("Enter your pickup location");
        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.d("Maps", "Place selected: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });

        placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.from_autocomplete);
        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                if(markerFrom == null) {
                    markerFrom = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getAddress().toString()));
                    latlngFrom = place.getLatLng();
                }
                else{
                    markerFrom.remove();
                    markerFrom = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getAddress().toString()));
                    latlngFrom = place.getLatLng();
                }
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
        // Add your locations to bounds using builder.include, maybe in a loop
                builder.include(place.getLatLng());
                builder.include(latlngTo);
                LatLngBounds bounds = builder.build();

        //Then construct a cameraUpdate
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 300);
        //Then move the camera
                mMap.animateCamera(cameraUpdate);
            }

            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });

        placeAutoCompleteTo = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.to_autocomplete);
        placeAutoCompleteTo.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                if(markerTo == null) {
                    markerTo = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getAddress().toString()));
                    latlngTo = place.getLatLng();
                }
                else{
                    markerTo.remove();
                    markerTo = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getAddress().toString()));
                    latlngTo = place.getLatLng();
                }
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                // Add your locations to bounds using builder.include, maybe in a loop
                builder.include(place.getLatLng());
                if(latlngFrom != null) {
                    builder.include(latlngFrom);
                }
                LatLngBounds bounds = builder.build();

                //Then construct a cameraUpdate
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 300);
                //Then move the camera
                mMap.animateCamera(cameraUpdate);
            }

            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        ((EditText)placeAutoComplete.getView().findViewById(R.id.place_autocomplete_search_input)).setTextSize(12.0f);
        placeAutoComplete.setHint("Enter your destination");
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    200);
        }
        else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {


            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (!bootLocation) {
                        bootLocation = true;
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        latlngTo = new LatLng(latitude, longitude);
                        Geocoder geocoder = new Geocoder(getApplicationContext());
                        try {
                            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                            String str = addressList.get(0).getAddressLine(0);

                            placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.to_autocomplete);
                            String value = str;
                            placeAutoComplete.setText(value);

                            markerTo = mMap.addMarker(new MarkerOptions().position(latlngTo).title(str));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngTo, 15.2f));


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

                        String str= addressList.get(0).getAddressLine(0);
                        placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.to_autocomplete);
                        String value = str;
                        placeAutoComplete.setText(value);
                        if(markerTo == null) {
                            markerTo = mMap.addMarker(new MarkerOptions().position(latlng).title(str));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15.2f));
                        }
                        else{
                            markerTo.remove();
                            markerTo = mMap.addMarker(new MarkerOptions().position(latlng).title(str));
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
    public void onMapReady (GoogleMap googleMap){
        mMap = googleMap;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 200: {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // {Some Code}
                }
            }
        }
    }

}