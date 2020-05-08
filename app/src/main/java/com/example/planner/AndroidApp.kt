package com.example.planner

import android.app.Application
import com.example.planner.presentation.di.AppComponent
import timber.log.Timber

class AndroidApp: Application() {

    private val appComponent by lazy { AppComponent.Initializer.init(this)}

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    fun getComponent() = appComponent
}