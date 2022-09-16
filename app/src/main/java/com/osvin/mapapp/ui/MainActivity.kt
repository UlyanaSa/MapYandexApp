package com.osvin.mapapp.ui
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.osvin.mapapp.R
import com.osvin.mapapp.databinding.ActivityMainBinding
import com.osvin.mapapp.models.CurrentLocation
import com.osvin.mapapp.utils.Constants
import com.osvin.mapapp.utils.GpsLocationUtil
import com.osvin.mapapp.utils.LocationPermissionUtil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bGoToMap.isClickable = false

        binding.bGoToMap.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
            finish()
        }

        LocationPermissionUtil.checkLocationPermissions(this, this::onLocationPermissionsGranted)

        if(GpsLocationUtil.isLocationEnabled(this)){
            binding.bGoToMap.isClickable = true
        }else{
            GpsLocationUtil.showAlertDialog(this)
        }

        getSaveLocation()


    }

    private fun getSaveLocation(){
        if(intent.extras != null){
            val bundle = intent.extras
            val lan = bundle!!.getString(Constants.LAN, "LAN")
            val lon = bundle!!.getString(Constants.LON, "LON")
            val currentLocation = CurrentLocation(lan.toDouble(), lon.toDouble())
            binding.tLocation.text = currentLocation.toString()
        }else
            binding.tLocation.text = getString(R.string.current_location)
    }

    private fun onLocationPermissionsGranted() {
        Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        LocationPermissionUtil.onRequestPermissionsResult(
            this,
            requestCode,
            this::onLocationPermissionsGranted
        )
    }
}





