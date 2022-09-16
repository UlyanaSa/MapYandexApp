package com.osvin.mapapp

import android.content.Context
import com.osvin.mapapp.data.MyLocationService
import com.osvin.mapapp.domain.GetPointAddressUseCase
import com.osvin.mapapp.models.CurrentLocation
import com.osvin.mapapp.models.PointAddress


class AppRepository (private val context: Context) {

    suspend fun getLocation(): CurrentLocation = MyLocationService(context).awaitLastLocation()

    fun getPointAddress(lan: Double, lon: Double):String = GetPointAddressUseCase(context).getPointAddress(lan, lon)
}