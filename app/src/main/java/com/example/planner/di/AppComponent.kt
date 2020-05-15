package com.example.planner.di


import android.content.Context
import com.example.planner.AndroidApp
import com.example.planner.domain.repositories.EventRepo
import com.example.planner.domain.repositories.GlobalRepo
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [AppModule::class]
)
@Singleton
interface AppComponent {

    fun provideAppContext(): Context
    fun provideEventRepo(): EventRepo
    fun provideGlobalRepo(): GlobalRepo

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