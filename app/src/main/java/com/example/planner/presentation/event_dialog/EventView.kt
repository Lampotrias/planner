package com.example.planner.presentation.event_dialog

import android.os.Bundle
import androidx.navigation.NavDirections
import com.example.planner.presentation.my_day.EventTransferObject
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.Skip

interface EventView: MvpView {
    @Skip
    fun close(eventObj: EventTransferObject)

    @AddToEnd
    fun showCalendarPopupDialog(navDirections: NavDirections)

    @AddToEnd
    fun showFormattedTime(year: Int, month: Int, day: Int, hours: Int, minutes: Int)

    @AddToEnd
    fun setEnableSubmit(enable: Boolean)
}