package com.osvin.mapapp.data

import android.content.Context
import com.osvin.mapapp.models.CurrentLocation
import com.osvin.mapapp.utils.Constants

class SharedPrefLocation(context: Context) {
    private var sharedPreferences =
        context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)

    fun saveLocation(currentLocation: CurrentLocation): Boolean {
        sharedPreferences.edit().putString(Constants.LAN, currentLocation.latitude.toString()).apply()
        sharedPreferences.edit().putString(Constants.LON, currentLocation.longitude.toString()).apply()
        return true
    }

    fun getCurrentLocation(): CurrentLocation {
        val lan = sharedPreferences.getString(Constants.LAN, "LAN") ?: "0.0"
        val lon = sharedPreferences.getString(Constants.LON, "LON") ?: "0.0"
        return CurrentLocation(lan.toDouble(), lon.toDouble())
    }
}