package com.lamp.planner.presentation.features.eventdialog

import androidx.navigation.NavDirections
import com.lamp.planner.domain.EventTransferObject
import com.lamp.planner.domain.excetion.Failure
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.Skip

interface EventView : MvpView {
    @Skip
    fun close(eventObj: EventTransferObject)

    @AddToEnd
    fun showCalendarPopupDialog(navDirections: NavDirections)

    @AddToEnd
    fun showGroupsPopupDialog(navDirections: NavDirections)

    @AddToEnd
    fun showFormattedTime(strDate: String, strTime: String, bAllDay: Int)

    @AddToEnd
    fun setEnableSubmit(enable: Boolean)

    @AddToEnd
    fun setGroupFormCaption(groupName: String)

    @Skip
    fun handleFailure(failure: Failure?)
}
