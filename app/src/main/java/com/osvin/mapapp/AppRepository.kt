package com.osvin.mapapp

import android.content.Context
import com.osvin.mapapp.data.MyLocationService
import com.osvin.mapapp.models.CurrentLocation


class AppRepository (private val context: Context) {

    suspend fun getLocation(): CurrentLocation = MyLocationService(context).awaitLastLocation()


}