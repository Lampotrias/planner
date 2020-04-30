package com.example.planner.presentation.planner_list.di

import com.example.planner.presentation.di.AppComponent
import com.example.planner.presentation.di.scope.PerFragment
import com.example.planner.presentation.planner_list.PlannerListFragment
import dagger.Component

@Component(
    dependencies = [AppComponent::class]
)
@PerFragment
interface PlannerListComponent {
    fun inject(fragment: PlannerListFragment)
}