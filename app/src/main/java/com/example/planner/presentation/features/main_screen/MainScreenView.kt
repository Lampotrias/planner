package com.example.planner.presentation.features.main_screen

import com.example.planner.domain.Group
import com.example.planner.domain.excetion.Failure
import com.example.planner.presentation.adapters.Manager
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

interface MainScreenView: MvpView {
    @AddToEndSingle
    fun showMessage(message: String)

    @AddToEnd
    fun openBottomSheet()

    @Skip
    fun handleFailure(failure: Failure?)

    @AddToEnd
    fun showGroups(
        manager: Manager<Group>,
        groups: List<Group>
    )
}