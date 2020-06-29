package com.lamp.planner.presentation.features.calendardetail.di

import com.lamp.planner.di.AppComponent
import com.lamp.planner.di.scope.PerFragment
import com.lamp.planner.presentation.features.calendardetail.CalendarDialog
import dagger.Component

@Component(
    dependencies = [AppComponent::class]
)
@PerFragment
interface CalendarComponent {
    fun inject(fragment: CalendarDialog)
}
