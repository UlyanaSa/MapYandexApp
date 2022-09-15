package com.osvin.mapapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.MapObject

class MapViewModel: ViewModel() {

    private var _currentPoint = MutableLiveData<Point>()
    var currentPoint: LiveData<Point> = _currentPoint

    private var _currentMapObject = MutableLiveData<MapObject>()
    var currentObject: LiveData<MapObject> = _currentMapObject

    private var _saveNew = MutableLiveData<Boolean>()
    var saveNew: LiveData<Boolean> = _saveNew

    fun saveMapObject(mapObject: MapObject){
        _currentMapObject.value = mapObject
        _saveNew.value = true
    }






}