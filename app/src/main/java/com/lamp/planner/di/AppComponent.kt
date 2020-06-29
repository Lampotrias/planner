package com.lamp.planner.di

import android.content.Context
import com.lamp.planner.AndroidApp
import com.lamp.planner.data.database.AppDatabase
import com.lamp.planner.domain.repositories.GlobalRepo
import com.lamp.planner.domain.repositories.RepoDb
import com.lamp.planner.presentation.background.alarm.AlarmTaskManager
import com.lamp.planner.presentation.background.workmanager.AppWorkerFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [AppModule::class, AppAssistedInjectModule::class, WorkerBindingModule::class]
)
@Singleton
interface AppComponent {

    fun provideAppContext(): Context
    fun provideEventRepo(): RepoDb
    fun provideGlobalRepo(): GlobalRepo
    fun provideDatabase(): AppDatabase
    fun provideAlarmManager(): AlarmTaskManager
    fun workerFactory(): AppWorkerFactory

    fun inject(application: AndroidApp)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: AndroidApp): AppComponent
    }

    class Initializer private constructor() {
        companion object {
            fun init(app: AndroidApp): AppComponent {
                return DaggerAppComponent.factory().create(app)
            }
        }
    }
}
