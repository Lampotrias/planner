package com.example.planner.presentation.features.calendardetail.di

import com.example.planner.di.AppComponent
import com.example.planner.di.scope.PerFragment
import com.example.planner.presentation.features.calendardetail.CalendarDialog
import dagger.Component

@Component(
    dependencies = [AppComponent::class]
)
@PerFragment
interface CalendarComponent {
    fun inject(fragment: CalendarDialog)
}
