package com.example.planner.presentation.main_screen.di

import com.example.planner.presentation.di.AppComponent
import com.example.planner.presentation.di.scope.PerFragment
import com.example.planner.presentation.main_screen.MainScreenFragment
import dagger.Component

@Component(
    dependencies = [AppComponent::class]
)
@PerFragment
interface MainScreenComponent {
    fun inject(fragment: MainScreenFragment)
}