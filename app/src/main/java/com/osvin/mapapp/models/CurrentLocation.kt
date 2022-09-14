package com.osvin.mapapp.models

import android.location.Geocoder
import com.yandex.mapkit.location.Location

data class CurrentLocation(
//    val city: String,
//    val street: String,
    val latitude: Geocoder,
    val longitude: Double
) {
    override fun toString(): String {
        return "Address (${latitude}, $longitude)"
    }
}