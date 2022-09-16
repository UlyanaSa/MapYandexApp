package com.osvin.mapapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osvin.mapapp.AppRepository
import com.osvin.mapapp.models.CurrentLocation
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.launch



class MapViewModel (private val appRepository: AppRepository): ViewModel() {

    private var _currentLocation = MutableLiveData<CurrentLocation>()
    var currentLocation: LiveData<CurrentLocation> = _currentLocation

    private var _saveCurrentLocation = MutableLiveData<CurrentLocation>()
    var saveCurrentLocation: LiveData<CurrentLocation> = _saveCurrentLocation

    fun savePoint(point: Point){
        _saveCurrentLocation.value = CurrentLocation(point.latitude, point.longitude)
    }


    fun getLocation(){
        viewModelScope.launch {
            _currentLocation.value = appRepository.getLocation()
        }
    }
}