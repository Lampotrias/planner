package com.example.planner.presentation.di

import android.content.Context
import com.example.planner.AndroidApp
import com.example.planner.data.database.RepoImpl
import com.example.planner.domain.EventRepo
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
        fun provideEventRepo(): EventRepo = RepoImpl()
    }
}