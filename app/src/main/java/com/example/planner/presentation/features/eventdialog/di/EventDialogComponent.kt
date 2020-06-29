package com.example.planner.presentation.features.eventdialog.di

import com.example.planner.di.AppComponent
import com.example.planner.di.scope.PerFragment
import com.example.planner.presentation.features.eventdialog.EventDialog
import dagger.Component

@Component(
    dependencies = [AppComponent::class]
)
@PerFragment
interface EventDialogComponent {
    fun inject(fragment: EventDialog)
}
