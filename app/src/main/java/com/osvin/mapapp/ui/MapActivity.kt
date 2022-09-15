package com.osvin.mapapp.ui

import android.Manifest
import android.R
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.Image
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationServices
import com.osvin.mapapp.databinding.ActivityMapBinding
import com.osvin.mapapp.vm.MapViewModel
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.*
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.runtime.ui_view.ViewProvider



class MapActivity : AppCompatActivity(), InputListener{
    private lateinit var binding: ActivityMapBinding
    private lateinit var mapViewModel: MapViewModel

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(MY_API_KEY)
        MapKitFactory.initialize(this)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapViewModel = ViewModelProvider(this)[MapViewModel::class.java]

        binding.mapview.map.isRotateGesturesEnabled = false
        checkPermissions()

        var lan: Double = 0.0
        var lon: Double = 0.0
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { task ->
            Log.d("TAG", "getUserLocation:  ${task.latitude}")
            Log.d("TAG", "getUserLocation:  ${task.longitude}")
            lan = task.latitude
            lon = task.longitude
        }
        binding.mapview.map.move(
            CameraPosition(CAMERA_TARGET, 14.0f, 0F, 0F),
            Animation(Animation.Type.SMOOTH, 1F),
            null
        )

        binding.mapview.map.addInputListener(this)
        
//        val locationManager = MapKitFactory.getInstance().createLocationManager()
//        locationManager.requestSingleUpdate(object : LocationListener {
//            override fun onLocationUpdated(p0: Location) {
//                Log.d("TAG", "onLocationUpdated: lat= ${p0?.position?.latitude} lon=${p0?.position?.longitude}")
//            }
//
//            override fun onLocationStatusUpdated(p0: LocationStatus) {
//                Log.d("TAG", "onLocation $p0")
//            }
//        })
      //  Log.d("TAG", "onCreate: posle")
        //val mapKit = MapKitFactory.getInstance()

   }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapview.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapview.onStop()
        MapKitFactory.getInstance().onStop()
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

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }

//    @SuppressLint("MissingSuperCall")
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        if (requestCode == MainActivity.permissionId) {
//            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//            }
//        }
//    }

    override fun onMapTap(map: Map, point: Point) {
        binding.mapview.map.deselectGeoObject()
        map.mapObjects.clear()
        val icon = ImageView(this)
        icon.setImageResource(R.drawable.ic_menu_mylocation)
        icon.setColorFilter(Color.RED)
        val viewProvider = ViewProvider(icon)
        binding.mapview.map.mapObjects.addPlacemark(point, viewProvider)
        Log.d("TAG", "onMapTap: ${point.latitude}, ${point.longitude}")
    }

    override fun onMapLongTap(p0: Map, p1: Point) {}

    companion object {
        const val MY_API_KEY = "99257b26-2933-48f9-a649-364582209dc6"
        const val permissionId = 10
        private val CAMERA_TARGET = Point(59.952, 30.318)
    }
}