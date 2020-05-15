package com.example.planner.presentation.calendar_detail

import moxy.MvpView
import moxy.viewstate.strategy.alias.Skip

interface CalendarView: MvpView {
    @Skip
    fun close()
}