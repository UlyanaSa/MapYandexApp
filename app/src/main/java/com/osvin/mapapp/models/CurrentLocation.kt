package com.osvin.mapapp.models


data class CurrentLocation(
    var latitude: Double,
    var longitude: Double
) {
    override fun toString(): String {
        return "Address (${latitude}, $longitude)"
    }
}