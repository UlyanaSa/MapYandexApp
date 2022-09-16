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
import com.osvin.mapapp.utils.Constants
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
   //     MapKitFactory.getInstance()
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appRepository = AppRepository(this)
        mapViewModel = ViewModelProvider(this, MapVMFactory(appRepository))[MapViewModel::class.java]

        binding.mapview.map.isRotateGesturesEnabled = false
        moveMap(Constants.CAMERA_TARGET)
        binding.mapview.map.addInputListener(this)
        mapViewModel.getLocation()

        mapViewModel.currentLocation.observe(this, Observer {
            if(it != null){
                moveMap(Point(it.latitude, it.longitude))
            }
        })

        val intent = Intent(this, MainActivity::class.java)
        binding.bBack.setOnClickListener {
            startActivity(intent)
            finish()
        }

        mapViewModel.saveCurrentLocation.observe(this, Observer {

            if(it == null){
                Toast.makeText(this, "Выберите точку", Toast.LENGTH_SHORT).show()
            }
            else{
                intent.putExtra(Constants.LAN, it.latitude.toString())
                intent.putExtra(Constants.LON, it.longitude.toString())
            }
        })

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
        mapViewModel.savePoint(point)
        Log.d("TAG", "onMapTap: ${point.latitude}, ${point.longitude}")

    }

    override fun onMapLongTap(p0: Map, p1: Point) {}

}