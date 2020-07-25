package com.lamp.planner

import android.app.Application
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AndroidApp : Application(), Configuration.Provider {
    //   @Inject lateinit var notificationHelper: NotificationHelper
//    @Inject lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        initNotification()
//        initWorkManager()
    }

    private fun initNotification() {
        //    notificationHelper.createChannels()
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
//            .setWorkerFactory(workerFactory)
            .build()
}
