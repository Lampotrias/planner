package com.lamp.planner.di

import android.app.AlarmManager
import android.content.Context
import com.lamp.planner.AndroidApp
import com.lamp.planner.data.GlobalRepoImpl
import com.lamp.planner.data.RepoDbImpl
import com.lamp.planner.data.database.AppDatabase
import com.lamp.planner.domain.repositories.GlobalRepo
import com.lamp.planner.domain.repositories.RepoDb
import com.lamp.planner.presentation.background.alarm.AlarmTaskManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Module
    companion object {
        @Singleton
        @Provides
        fun provideContext(app: AndroidApp): Context = app.applicationContext

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
