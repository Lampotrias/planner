package com.example.planner.presentation.calendar_detail

import android.os.Bundle
import com.example.planner.presentation.my_day.EventTransferObject
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.Skip

interface CalendarView: MvpView {
    @Skip
    fun close(eventObj: EventTransferObject)

    @AddToEnd
    fun showDate(hours: Int, minutes: Int, title: String)

    @AddToEnd
    fun initTimeControl(hours: Int, minutes: Int)

    @AddToEnd
    fun initCalendarControl(year: Int, month: Int, day: Int)

}