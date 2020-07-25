package com.lamp.planner.di

import android.app.AlarmManager
import android.app.Application
import android.content.Context
import com.lamp.planner.data.GlobalRepoImpl
import com.lamp.planner.data.RepoDbImpl
import com.lamp.planner.data.database.AppDatabase
import com.lamp.planner.domain.repositories.GlobalRepo
import com.lamp.planner.domain.repositories.RepoDb
import com.lamp.planner.presentation.background.alarm.AlarmTaskManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApplicationModule {
    @Singleton
    @Provides
    fun provideContext(app: Application): Context = app.applicationContext

    @Singleton
    @Provides
    fun provideEventRepo(appDatabase: AppDatabase): RepoDb = RepoDbImpl(appDatabase)

    @Singleton
    @Provides
    fun provideGlobalRepo(): GlobalRepo = GlobalRepoImpl()

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase = AppDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideAlarmManager(context: Context): AlarmTaskManager {
        return AlarmTaskManager(
            context,
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        )
    }
}
