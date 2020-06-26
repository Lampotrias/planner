package com.example.planner.presentation.features.auth.di

import com.example.planner.di.AppComponent
import com.example.planner.di.scope.PerFragment
import com.example.planner.presentation.features.auth.AuthFragment
import dagger.Component

@Component(
    dependencies = [AppComponent::class]
)
@PerFragment
interface AuthComponent {
    fun inject(fragment: AuthFragment)
}
