package com.lamp.planner.presentation.features.eventdialog.di

import com.lamp.planner.di.AppComponent
import com.lamp.planner.di.scope.PerFragment
import com.lamp.planner.presentation.features.eventdialog.EventDialog
import dagger.Component

@Component(
    dependencies = [AppComponent::class]
)
@PerFragment
interface EventDialogComponent {
    fun inject(fragment: EventDialog)
}
