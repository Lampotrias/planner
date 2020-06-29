package com.lamp.planner.presentation.features.myday

import androidx.navigation.NavDirections
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.presentation.DisplayableItem
import com.lamp.planner.presentation.adapters.ManagerImpl
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

interface MyDayView : MvpView {
    @AddToEndSingle
    fun showAnimationFab(offset: Float)

    @AddToEnd
    fun showAddEventPopupDialog(navDirections: NavDirections)

    @Skip
    fun handleFailure(failure: Failure?)

    @AddToEnd
    fun showEventList(managerImpl: ManagerImpl<DisplayableItem>, list: List<DisplayableItem>)

    @Skip
    fun showSuccessEventAddMessage()
}
