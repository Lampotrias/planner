package com.lamp.planner.presentation.features.calendardialog

import androidx.navigation.NavDirections
import com.lamp.planner.domain.EventTransferObject
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.presentation.features.NotifyTimeInterval
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.Skip

interface CalendarView : MvpView {
    @Skip
    fun close(eventObj: EventTransferObject)

    @AddToEnd
    fun showFormattedTime(strDate: String, strTime: String, bAllDay: Int)

    @AddToEnd
    fun initTimeControl(hours: Int, minutes: Int)

    @AddToEnd
    fun initCalendarControl(year: Int, month: Int, day: Int)

    @Skip
    fun handleFailure(failure: Failure?)

    @Skip
    fun navigateReminderDialog(navDirections: NavDirections)

    @Skip
    fun navigateRepeatDialog(navDirections: NavDirections)

    @AddToEnd
    fun updateReminderStatus(timeInterval: NotifyTimeInterval)
}
