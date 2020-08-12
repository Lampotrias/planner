package com.lamp.planner.presentation.features.repeatdialog

import com.lamp.planner.domain.RepeatInterval
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.Skip

interface RepeatParamView : MvpView {
    @AddToEnd
    fun setAlways(status: String, periodName: String)

    @AddToEnd
    fun setUntilDate(status: String, periodName: String)

    @AddToEnd
    fun setCount(status: String, periodName: String)

    @AddToEnd
    fun showChildParamsDialog(setShow: Boolean, count: Int = 1)

    @Skip
    fun close(repeatObject: RepeatInterval)

    @AddToEnd
    fun setRepeatInterval(repeatInterval: Int)

    @AddToEnd
    fun openCalendar()
}
