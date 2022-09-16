package com.osvin.mapapp.ui
import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.osvin.mapapp.R
import com.osvin.mapapp.databinding.ActivityMainBinding
import com.osvin.mapapp.models.CurrentLocation
import com.osvin.mapapp.utils.Constants
import com.osvin.mapapp.utils.GpsLocationUtil



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = Intent(this, MapActivity::class.java)

        binding.bGoToMap.setOnClickListener {
            if (checkPermissions() || checkBackgroundLocation()) {
                if (GpsLocationUtil.isLocationEnabled(this)) {
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.please_enable_location),
                        Toast.LENGTH_LONG
                    ).show()
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                }
            } else {
                requestBackgroundLocationPermission()
            }
        }
        getSaveLocation()
    }


    private fun getSaveLocation() {
        if (intent.extras != null) {
            val bundle = intent.extras
            val lan = bundle!!.getString(Constants.LAN, "LAN")
            val lon = bundle!!.getString(Constants.LON, "LON")
            val currentLocation = CurrentLocation(lan.toDouble(), lon.toDouble())
            binding.tLocation.text = currentLocation.toString()
        } else
            binding.tLocation.text = getString(R.string.current_location)
    }


    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    @SuppressLint("InlinedApi")
    private fun checkBackgroundLocation():Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            Constants.REQUEST_CODE_FOREGROUND
        )
    }

    private fun requestBackgroundLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ),
                Constants.REQUEST_CODE_FOREGROUND_AND_BACKGROUND
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                Constants.REQUEST_CODE_FOREGROUND
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constants.REQUEST_CODE_FOREGROUND -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        checkBackgroundLocation()
                    }
                } else {
                    Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_LONG)
                        .show()
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    ) {
                        startActivity(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", this.packageName, null),
                            ),
                        )
                    }
                }
                return
            }
            Constants.REQUEST_CODE_FOREGROUND_AND_BACKGROUND -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Toast.makeText(
                            this,
                            getString(R.string.success),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_LONG).show()
                    startActivity(
                        Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", this.packageName, null),
                        ),
                    )
                }
                return
            }
        }
    }
}





