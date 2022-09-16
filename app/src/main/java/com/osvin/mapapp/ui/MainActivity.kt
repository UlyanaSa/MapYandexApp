package com.osvin.mapapp.ui
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.osvin.mapapp.R
import com.osvin.mapapp.databinding.ActivityMainBinding
import com.osvin.mapapp.utils.LocationPermissionUtil
import com.osvin.mapapp.utils.GpsLocationUtil

class MainActivity : AppCompatActivity() {
    val TAG = "TAG"
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
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
        }else{
            GpsLocationUtil.showAlertDialog(this)
        }



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





