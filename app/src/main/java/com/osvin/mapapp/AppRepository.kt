package com.osvin.mapapp

import android.app.Application
import android.content.Context
import com.osvin.mapapp.data.MyLocationService
import com.osvin.mapapp.data.SharedPrefLocation
import com.osvin.mapapp.models.CurrentLocation

class AppRepository(private val context: Context, private val sp: SharedPrefLocation) {

    suspend fun getLocation(): CurrentLocation = MyLocationService(context).awaitLastLocation()

    fun saveLocation(currentLocation: CurrentLocation)
}