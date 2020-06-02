package com.example.planner.di

import android.app.AlarmManager
import android.content.Context
import com.example.planner.AndroidApp
import com.example.planner.data.GlobalRepoImpl
import com.example.planner.data.RepoDbImpl
import com.example.planner.data.database.AppDatabase
import com.example.planner.domain.repositories.RepoDb
import com.example.planner.domain.repositories.GlobalRepo
import com.example.planner.presentation.background.alarm.AlarmTaskManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Module
    companion object {
        @Singleton
        @Provides
        fun provideContext(app: AndroidApp):Context = app.applicationContext

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
}