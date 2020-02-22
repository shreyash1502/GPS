package com.example.gps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private TextView txt1;
    private TextView txt2;
    private TextView txt3;
    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt1 = (TextView) findViewById(R.id.textView1);
        txt2 = (TextView) findViewById(R.id.textView2);
        txt3 = (TextView) findViewById(R.id.textView3);
        btn = (Button) findViewById(R.id.button);
        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        boolean isGPS_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isGPS_enabled) {
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    try {
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        txt1.setText(addressList.get(0).getAddressLine(0));
                        txt2.setText(addressList.get(0).getAdminArea());
                        txt3.setText(addressList.get(0).getCountryName());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {
                    txt1.setText("Fuck U");

                }
            };
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String [] {Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
else{
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);


        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length >0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
    txt1.setText("Getting Location");
    txt2.setText("Getting Location");
    txt3.setText("Getting Location");
        }
}else{txt1.setText("Permission Not Greanted");
            txt2.setText("Permission Not Greanted");
            txt3.setText("Permission Not Greanted");

        }
    }
}