package com.osvin.mapapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osvin.mapapp.AppRepository
import com.osvin.mapapp.models.CurrentLocation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.MapObject
import kotlinx.coroutines.launch

class MapViewModel(private val appRepository: AppRepository): ViewModel() {

    private var _currentLocation = MutableLiveData<CurrentLocation>()
    var currentLocation: LiveData<CurrentLocation> = _currentLocation

    fun getLocation(){
        viewModelScope.launch {
            _currentLocation.value = appRepository.getLocation()
        }
    }
}