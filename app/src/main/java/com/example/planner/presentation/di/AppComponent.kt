package com.example.planner.presentation.di


import android.content.Context
import com.example.planner.AndroidApp
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [AppModule::class]
)
@Singleton
interface AppComponent {

    fun provideAppContext(): Context

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun app(application: AndroidApp): Builder
    }

    class Initializer private constructor() {
        companion object {
            fun init(app: AndroidApp): AppComponent {
                return DaggerAppComponent.builder().app(app)
                    .build()
            }
        }
    }
}