package com.lamp.planner.presentation.features.calendardialog

import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.presentation.features.myday.EventTransferObject
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
}
