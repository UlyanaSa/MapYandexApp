package com.osvin.mapapp.models

import java.io.Serializable

data class CurrentLocation (
    var latitude: Double,
    var longitude: Double,
)
{
    override fun toString(): String {
        return "(${latitude}, $longitude)"
    }
}