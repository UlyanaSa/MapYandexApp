package com.osvin.mapapp.data

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.*
import com.osvin.mapapp.models.CurrentLocation
import com.osvin.mapapp.models.PointAddress
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class MyLocationService(private val context: Context){

    private val fusedLocationProvider = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    suspend fun awaitLastLocation(): CurrentLocation {
        return suspendCoroutine<CurrentLocation> { cont ->
            fusedLocationProvider.lastLocation.addOnSuccessListener { location ->
                cont.resume(CurrentLocation(location.latitude, location.longitude))
            }.addOnFailureListener { e ->
                cont.resumeWithException(e)
            }
        }
    }
}


