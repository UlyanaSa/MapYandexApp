package com.osvin.mapapp.ui

import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.osvin.mapapp.AppRepository
import com.osvin.mapapp.databinding.ActivityMapBinding
import com.osvin.mapapp.utils.CameraPositionState
import com.osvin.mapapp.utils.Constants
import com.osvin.mapapp.utils.GpsLocationUtil
import com.osvin.mapapp.vm.MapVMFactory
import com.osvin.mapapp.vm.MapViewModel
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.*
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.runtime.ui_view.ViewProvider


class MapActivity : AppCompatActivity(), InputListener{
    private lateinit var binding: ActivityMapBinding
    private lateinit var mapViewModel: MapViewModel

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appRepository = AppRepository(this)
        mapViewModel = ViewModelProvider(this, MapVMFactory(appRepository))[MapViewModel::class.java]

        binding.mapview.map.isRotateGesturesEnabled = false
        moveMap(Constants.CAMERA_TARGET)

        binding.mapview.map.addInputListener(this)

        enabledLocation()
        observeCurrentLocation()

        val intent = Intent(this, MainActivity::class.java)

        observeSaveAddressPoint(intent)
        binding.bBack.setOnClickListener {
            startActivity(intent)
            finish()
        }

        observeCameraPosition()
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

    private fun enabledLocation(){
        if(GpsLocationUtil.isLocationEnabled(this)){
            mapViewModel.getLocation()
        }else
            GpsLocationUtil.showAlertDialog(this)
    }

    private fun observeCameraPosition(){
        mapViewModel.cameraPositionLiveData.observe(this, Observer {
            if(it == CameraPositionState.UNDER15){
                Toast.makeText(this, "При таком зуме сложно определить адрес", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun observeCurrentLocation(){
        mapViewModel.currentLocationLiveData.observe(this, Observer {
            if(it != null){
                moveMap(Point(it.latitude, it.longitude))
            }
        })
    }

    private fun observeSaveAddressPoint(intent: Intent){
        mapViewModel.saveAddressLiveData.observe(this, Observer {
            if(it == null){
                Toast.makeText(this, "Выберите точку", Toast.LENGTH_SHORT).show()
            }
            else{
                intent.putExtra(Constants.ADDRESS, it)
            }
        })
    }
    private fun moveMap(point:Point){
        binding.mapview.map.move(
            CameraPosition(point, 14.0f, 0F, 0F),
            Animation(Animation.Type.SMOOTH, 1F),
            null
        )
    }

    override fun onMapTap(map: Map, point: Point) {

        binding.mapview.map.deselectGeoObject()
        map.mapObjects.clear()
        val icon = ImageView(this)
        icon.setImageResource(R.drawable.ic_menu_mylocation)
        icon.setColorFilter(Color.RED)
        val viewProvider = ViewProvider(icon)
        binding.mapview.map.mapObjects.addPlacemark(point, viewProvider)
        mapViewModel.getPointAddress(point.latitude, point.longitude)
        val cameraPosition = binding.mapview.map.cameraPosition.zoom
        mapViewModel.cameraPositionState(cameraPosition)

    }

    override fun onMapLongTap(p0: Map, p1: Point) {}
}