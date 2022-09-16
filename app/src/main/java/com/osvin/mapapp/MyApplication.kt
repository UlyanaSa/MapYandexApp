package com.osvin.mapapp

import android.app.Application
import com.osvin.mapapp.utils.Constants
import com.yandex.mapkit.MapKitFactory

class MyApplication : Application() {
    override fun onCreate() {
        MyApplication.mInstance = this
        MapKitFactory.setApiKey(Constants.MY_API_KEY)

        super.onCreate()
    }

    companion object {
        private var mInstance: MyApplication? = null
        val instance: MyApplication?
            get() = MyApplication.mInstance
    }
}

