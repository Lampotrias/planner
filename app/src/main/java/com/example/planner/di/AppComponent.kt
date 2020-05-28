package com.example.planner.di


import android.content.Context
import com.example.planner.AndroidApp
import com.example.planner.data.database.AppDatabase
import com.example.planner.domain.repositories.EventRepo
import com.example.planner.domain.repositories.GlobalRepo
import com.example.planner.presentation.background.alarm.AlarmTaskManager
import com.example.planner.presentation.background.workmanager.AppWorkerFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [AppModule::class, AppAssistedInjectModule::class, WorkerBindingModule::class]
)
@Singleton
interface AppComponent {

    fun provideAppContext(): Context
    fun provideEventRepo(): EventRepo
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