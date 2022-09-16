package com.osvin.mapapp.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.osvin.mapapp.AppRepository
import java.security.InvalidParameterException

class MapVMFactory (private val appRepository: AppRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            return MapViewModel(appRepository) as T
        }
        throw InvalidParameterException("ViewModel Not Found")
    }
}
