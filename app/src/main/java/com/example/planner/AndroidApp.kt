package com.example.planner

import android.app.Application
import com.example.planner.presentation.di.AppComponent

class AndroidApp: Application() {

    private val appComponent by lazy { AppComponent.Initializer.init(this)}

    override fun onCreate() {
        super.onCreate()
    }

    fun getComponent() = appComponent
}