package com.example.planner.presentation.my_day.di

import com.example.planner.presentation.di.AppComponent
import com.example.planner.presentation.di.scope.PerFragment
import com.example.planner.presentation.my_day.MyDayFragment
import dagger.Component

@Component(
    dependencies = [AppComponent::class]
)
@PerFragment
interface MyDayComponent {
    fun inject(fragment: MyDayFragment)
}