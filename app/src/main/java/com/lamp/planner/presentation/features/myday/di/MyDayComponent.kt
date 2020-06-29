package com.lamp.planner.presentation.features.myday.di

import com.lamp.planner.di.AppComponent
import com.lamp.planner.di.scope.PerFragment
import com.lamp.planner.presentation.features.myday.MyDayFragment
import dagger.Component

@Component(
    dependencies = [AppComponent::class]
)
@PerFragment
interface MyDayComponent {
    fun inject(fragment: MyDayFragment)
}
