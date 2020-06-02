package com.example.planner.presentation.features.my_day.di

import com.example.planner.di.AppComponent
import com.example.planner.di.scope.PerFragment
import com.example.planner.presentation.features.my_day.MyDayFragment
import dagger.Component

@Component(
    dependencies = [AppComponent::class]
)
@PerFragment
interface MyDayComponent {
    fun inject(fragment: MyDayFragment)
}