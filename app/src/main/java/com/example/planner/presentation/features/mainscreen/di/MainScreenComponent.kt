package com.example.planner.presentation.features.mainscreen.di

import com.example.planner.di.AppComponent
import com.example.planner.di.scope.PerFragment
import com.example.planner.presentation.features.mainscreen.MainScreenFragment
import dagger.Component

@Component(
    dependencies = [AppComponent::class]
)
@PerFragment
interface MainScreenComponent {
    fun inject(fragment: MainScreenFragment)
}
