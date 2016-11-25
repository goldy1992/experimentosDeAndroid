package com.example.mike.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import java.io.IOException;
import java.util.Locale;
import static android.location.LocationManager.NETWORK_PROVIDER;

/**
 * Created by Mike on 25/11/2016.
 */

public class MyLocationListener implements LocationListener {

    // Acquire a reference to the system Location Manager
    LocationManager locationManager = null;

    private MainActivity activity;
    private Geocoder geocoder;
    private Location londres;


    public MyLocationListener(MainActivity activity) {
        this.activity = activity;
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        getLocationOfLondres();
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //return TODO;
        }
        locationManager.requestLocationUpdates(NETWORK_PROVIDER, 0, 0, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            Address addressOfLocation = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0);
            activity.updateLocation(addressOfLocation, location.distanceTo(getLocationOfLondres()));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
    @Override
    public void onProviderEnabled(String provider) {    }
    @Override
    public void onProviderDisabled(String provider) {    }

    private Location getLocationOfLondres() {
        if (null != this.londres) {
            return this.londres;
        }
        if (null == geocoder) {
            geocoder = new Geocoder(activity.getBaseContext(), Locale.getDefault());
        }
        Location londres = null;
        try {
            Address addressOfLondres = geocoder.getFromLocationName("London", 1).get(0);
            londres = new Location("");
            londres.setLatitude(addressOfLondres.getLatitude());
            londres.setLongitude(addressOfLondres.getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
        }
            return londres;
    }
}
