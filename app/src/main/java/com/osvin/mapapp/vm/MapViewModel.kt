package com.osvin.mapapp.vm
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osvin.mapapp.AppRepository
import com.osvin.mapapp.models.CurrentLocation
import com.osvin.mapapp.utils.CameraPositionState
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import kotlinx.coroutines.launch



class MapViewModel (private val appRepository: AppRepository): ViewModel() {

    private var _currentLocationLiveData = MutableLiveData<CurrentLocation>()
    var currentLocationLiveData: LiveData<CurrentLocation> = _currentLocationLiveData

    private var _saveAddressLiveData = MutableLiveData<String>()
    var saveAddressLiveData: LiveData<String> = _saveAddressLiveData

    private var _cameraPositionLiveData = MutableLiveData<CameraPositionState>()
    var cameraPositionLiveData: LiveData<CameraPositionState> = _cameraPositionLiveData

    fun getPointAddress(lan: Double, lon: Double){
        _saveAddressLiveData.value = appRepository.getPointAddress(lan, lon)
    }

    fun getLocation(){
        viewModelScope.launch {
            _currentLocationLiveData.value = appRepository.getLocation()
        }
    }

    fun cameraPositionState(cameraPosition: Float){
        if(cameraPosition>=15)
            _cameraPositionLiveData.value = CameraPositionState.OVER15
        else
            _cameraPositionLiveData.value = CameraPositionState.UNDER15
    }
}