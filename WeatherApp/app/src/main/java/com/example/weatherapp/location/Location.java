package com.example.weatherapp.location;

import android.annotation.SuppressLint;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

import java.util.concurrent.TimeUnit;

public class Location {
    public MutableLiveData<double[]> latLon = new MutableLiveData<>();
    @SuppressLint("MissingPermission")
    public void getLocationData(AppCompatActivity activity, Boolean isLocationPermissionGranted) {
        FusedLocationProviderClient fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(activity);

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(TimeUnit.SECONDS.toMillis(60));
        locationRequest.setFastestInterval(TimeUnit.SECONDS.toMillis(30));
        locationRequest.setMaxWaitTime(TimeUnit.MINUTES.toMillis(2));
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationCallback locationCallback = new LocationCallback() {
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                latLon.postValue(
                        new double[]{locationResult.getLastLocation().getLatitude(),
                                locationResult.getLastLocation().getLongitude()}
                );
                //remove location update
                fusedLocationProviderClient.removeLocationUpdates(this);
            }
        };


        if (isLocationPermissionGranted) {
            fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.myLooper()
            );
        }
    }
}
