package com.osvin.mapapp.domain

import android.content.Context
import android.location.Geocoder
import com.osvin.mapapp.models.PointAddress
import java.util.*

class GetPointAddressUseCase(private val context: Context) {

    fun getPointAddress(latitude: Double, longitude: Double): String {
        val lan = latitude.toString().substring(0,9)
        val lon = longitude.toString().substring(0,9)
        var result = "Невозможно определить адрес ($lan, $lon)"
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addressPoint = geocoder.getFromLocation(latitude, longitude, 1).map { address ->
                val country = address.countryName
                val street = address.thoroughfare
                val city = address.locality
                val lon = address.longitude.toString().substring(0, 9)
                val lan = address.latitude.toString().substring(0,9)

                val pointAddress = PointAddress(country = country, city = city, street = street, latitude = lan, longitude = lon).toString()
                result = pointAddress
            }
        } catch (e: Exception) {
             result = "Невозможно определить адрес ($lan, $lon)"
        }
        return result
    }
}