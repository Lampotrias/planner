package com.lamp.planner.presentation.features.grouplistdialog

import com.lamp.planner.domain.Group
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.presentation.adapters.ManagerImpl
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.Skip

interface GroupListView : MvpView {
    @Skip
    fun close(group: Group)

    @AddToEnd
    fun showGroups(
        managerImpl: ManagerImpl<Group>,
        groups: List<Group>,
        position: Int
    )

    @Skip
    fun handleFailure(failure: Failure?)
}
