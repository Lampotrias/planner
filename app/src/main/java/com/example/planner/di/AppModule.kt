package com.example.planner.di

import android.content.Context
import com.example.planner.AndroidApp
import com.example.planner.data.database.GlobalRepoImpl
import com.example.planner.data.database.RepoImpl
import com.example.planner.domain.repositories.EventRepo
import com.example.planner.domain.repositories.GlobalRepo
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

        @Singleton
        @Provides
        fun provideGlobalRepo(): GlobalRepo = GlobalRepoImpl()
    }
}