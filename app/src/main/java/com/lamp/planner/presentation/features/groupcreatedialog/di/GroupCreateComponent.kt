package com.lamp.planner.presentation.features.groupcreatedialog.di

import com.lamp.planner.di.AppComponent
import com.lamp.planner.di.scope.PerFragment
import com.lamp.planner.presentation.features.groupcreatedialog.CreateGroupDialog
import dagger.Component

@Component(
    dependencies = [AppComponent::class]
)
@PerFragment
interface GroupCreateComponent {
    fun inject(fragment: CreateGroupDialog)
}
