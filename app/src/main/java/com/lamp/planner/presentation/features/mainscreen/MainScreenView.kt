package com.lamp.planner.presentation.features.mainscreen

import androidx.navigation.NavDirections
import com.lamp.planner.domain.Group
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.presentation.adapters.ManagerImpl
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

interface MainScreenView : MvpView {
    @AddToEndSingle
    fun showMessage(message: String)

    @AddToEnd
    fun navigateMyDay(resId: Int)

    @Skip
    fun handleFailure(failure: Failure?)

    @Skip
    fun navigateAuth(navDirections: NavDirections)

    @Skip
    fun navigateCreateGroupDialog(navDirections: NavDirections)

    @AddToEnd
    fun showGroups(
        managerImpl: ManagerImpl<Group>,
        groups: List<Group>
    )

    @AddToEnd
    fun initAuthorizeForm()

    @AddToEnd
    fun setAccountCation(name: String)
}
