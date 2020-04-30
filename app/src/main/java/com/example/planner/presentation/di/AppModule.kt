package com.example.planner.presentation.di

import android.content.Context
import com.example.planner.AndroidApp
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
    }
}