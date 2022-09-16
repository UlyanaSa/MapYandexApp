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

    private var _savePoint = MutableLiveData<CurrentLocation>()
    var savePoint: LiveData<CurrentLocation> = _savePoint

    fun savePointfunc(point: Point){
        _savePoint.value = CurrentLocation(point.latitude, point.longitude)
    }


    fun getLocation(){
        viewModelScope.launch {
            _currentLocation.value = appRepository.getLocation()
        }
    }
}