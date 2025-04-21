package com.rockrecap

import android.app.Application
import com.rockrecap.data.AppContainer
import com.rockrecap.data.AppDataContainer

class RockRecapApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}