package com.example.planner

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.planner.di.AppComponent
import com.example.planner.presentation.background.NotificationHelper
import timber.log.Timber
import javax.inject.Inject

class AndroidApp : Application() {
    private val appComponent by lazy { AppComponent.Initializer.init(this) }

    @Inject
    lateinit var notificationHelper: NotificationHelper

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        onInitDependencyInjection()
        initNotification()
        initWorkManager()
    }

    private fun initWorkManager() {
        WorkManager.initialize(
            this,
            Configuration.Builder().setWorkerFactory(appComponent.workerFactory()).build()
        )
    }

    private fun initNotification() {
        notificationHelper.createChannels()
    }

    fun getComponent() = appComponent

    private fun onInitDependencyInjection() {
        appComponent.inject(this)
    }

}