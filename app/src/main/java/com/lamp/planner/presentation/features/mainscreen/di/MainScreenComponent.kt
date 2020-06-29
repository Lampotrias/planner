package com.lamp.planner.presentation.features.mainscreen.di

import com.lamp.planner.di.AppComponent
import com.lamp.planner.di.scope.PerFragment
import com.lamp.planner.presentation.features.mainscreen.MainScreenFragment
import dagger.Component

@Component(
    dependencies = [AppComponent::class]
)
@PerFragment
interface MainScreenComponent {
    fun inject(fragment: MainScreenFragment)
}
