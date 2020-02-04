package edu.temple.androidlocationservices_2_4_2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;

    TextView latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitude = findViewById(R.id.latitudeTxt);
        longitude = findViewById(R.id.longitudeTxt);

        locationManager = getSystemService(LocationManager.class);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude.setText(String.valueOf(location.getLatitude()));
                longitude.setText(String.valueOf(location.getLongitude()));
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 111);
        } else {
            showLocationUpdates();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showLocationUpdates();
        }
    }

    @SuppressLint("MissingPermission") // Already checking permissions before calling
    private void showLocationUpdates() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener); // GPS
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener); // Cell sites
        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, locationListener); // WiFi
    }

    // Need a way to remove location updates, otherwise you could get a memory leak.
    // LocationManager is a reference to the system service for locations, could lose access
    // to this specific instance if Activity is restarted
    private void stopLocationUpdates() {
        locationManager.removeUpdates(locationListener);
    }
}
