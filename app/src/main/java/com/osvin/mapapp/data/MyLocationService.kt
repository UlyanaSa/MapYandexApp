package com.osvin.mapapp.data

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.gms.location.*

import com.osvin.mapapp.models.CurrentLocation
import com.osvin.mapapp.utils.GpsLocationUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class MyLocationService(context: Context) {


//    companion object {
//        private val locationRequest: LocationRequest = LocationRequest.create().apply {
//            interval = 60000
//            fastestInterval = 10000
//            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//        }
//    }
    private var fusedLocationProvider = LocationServices.getFusedLocationProviderClient(context)

//    private var locationCallback: LocationCallback = object : LocationCallback() {
//        override fun onLocationResult(locationResult: LocationResult) {
//            val locationList = locationResult.locations
//            if (locationList.isNotEmpty()) {
//
//                val location = locationList.last()
//
//            }
//        }
//    }
//
//    fun start(){
//
//    }
//
//    fun stop(){
//        fusedLocationProvider.removeLocationUpdates(locationCallback)
//    }

//    @SuppressLint("MissingPermission")
//    private fun startLocationUpdates() {
//        fusedLocationProvider.requestLocationUpdates(locationRequest, locationCallback, null)
//    }


    @SuppressLint("MissingPermission")
    suspend fun awaitLastLocation(): CurrentLocation {
        return suspendCoroutine<CurrentLocation> { cont ->
            fusedLocationProvider.lastLocation.addOnSuccessListener { location ->
                // Resume coroutine and return location
                cont.resume(CurrentLocation(location.latitude, location.longitude))
            }.addOnFailureListener { e ->
                // Resume the coroutine by throwing an exception
                cont.resumeWithException(e)
            }
        }
    }

}


