package com.osvin.mapapp.data

import android.content.Context
import com.yandex.mapkit.MapKitFactory

class MapkitInit {
    private var initialized = false

    fun initialize(apiKey: String, context: Context) {
        if (initialized) {
            return
        }

        MapKitFactory.setApiKey(apiKey)
        MapKitFactory.initialize(context)
        initialized = true
    }
}