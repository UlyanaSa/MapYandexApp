package com.osvin.mapapp.data

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.*
import com.osvin.mapapp.models.CurrentLocation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class MyLocationService(private val context: Context){

    private val fusedLocationProvider = FusedLocationProviderClient(context)

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


