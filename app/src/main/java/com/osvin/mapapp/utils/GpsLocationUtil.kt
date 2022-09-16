package com.osvin.mapapp.utils
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import com.osvin.mapapp.R


object GpsLocationUtil {

    fun isLocationEnabled(context: Context): Boolean {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    fun showAlertDialog(context: Context) {
        AlertDialog.Builder(context)
            .setMessage(context.getString(R.string.please_enable_location))
            .setCancelable(false)
            .setPositiveButton(context.getString(R.string.enable_now)) { _, _ ->
                context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .show()
    }

}