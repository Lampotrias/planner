package com.example.planner.presentation.features.group_list_dialog.di

import com.example.planner.di.AppComponent
import com.example.planner.di.scope.PerFragment
import com.example.planner.presentation.features.group_list_dialog.GroupListDialog
import dagger.Component

@Component(
    dependencies = [AppComponent::class]
)
@PerFragment
interface GroupListDialogComponent {
    fun inject(fragment: GroupListDialog)
}