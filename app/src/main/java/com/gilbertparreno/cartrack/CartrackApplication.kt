package com.gilbertparreno.cartrack

import android.app.Application
import com.gilbertparreno.cartrack.core.networking.di.NetworkModule
import com.gilbertparreno.cartrack.core.di.AppComponent
import com.gilbertparreno.cartrack.core.di.DaggerAppComponent
import timber.log.Timber

class CartrackApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        appComponent = DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .build()
    }
}