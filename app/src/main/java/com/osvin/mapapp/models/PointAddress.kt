package com.osvin.mapapp.models

import java.io.Serializable

data class PointAddress (
    var country: String,
    var city: String,
    var street: String,
    var latitude: String,
    var longitude: String) {
    override fun toString(): String {
        return "$country, $city, $street, (${latitude}, $longitude)"
    }
}