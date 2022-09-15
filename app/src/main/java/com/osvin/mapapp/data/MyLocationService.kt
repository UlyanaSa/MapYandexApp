package com.osvin.mapapp.data

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.gms.location.*

import com.osvin.mapapp.models.CurrentLocation
import kotlin.coroutines.suspendCoroutine

class MyLocationService(private val context: Context) {


    private val locationRequest: LocationRequest = LocationRequest.create()
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)


    private fun isLocationEnabled(): Boolean {
        val locationService = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationService.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationService.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    @SuppressLint("MissingPermission")
    fun getLocation(): CurrentLocation{
        val currentLocation = CurrentLocation(0.0, 0.0)
        if(isLocationEnabled()){
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                currentLocation.latitude = location.latitude
                currentLocation.longitude = location.longitude

            }
        }else{
            Toast.makeText(context, "Пожалуйста, включите определение местоположения", Toast.LENGTH_LONG).show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            context.startActivity(intent)
        }
        return currentLocation
    }



    suspend fun getlocation(): CurrentLocation = suspendCoroutine { continuation ->


    }
}