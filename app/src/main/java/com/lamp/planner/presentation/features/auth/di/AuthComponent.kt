package com.lamp.planner.presentation.features.auth.di

import com.lamp.planner.di.AppComponent
import com.lamp.planner.di.scope.PerFragment
import com.lamp.planner.presentation.features.auth.AuthFragment
import dagger.Component

@Component(
    dependencies = [AppComponent::class]
)
@PerFragment
interface AuthComponent {
    fun inject(fragment: AuthFragment)
}
