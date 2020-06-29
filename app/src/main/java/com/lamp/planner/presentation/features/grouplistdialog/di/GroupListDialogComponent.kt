package com.lamp.planner.presentation.features.grouplistdialog.di

import com.lamp.planner.di.AppComponent
import com.lamp.planner.di.scope.PerFragment
import com.lamp.planner.presentation.features.grouplistdialog.GroupListDialog
import dagger.Component

@Component(
    dependencies = [AppComponent::class]
)
@PerFragment
interface GroupListDialogComponent {
    fun inject(fragment: GroupListDialog)
}
