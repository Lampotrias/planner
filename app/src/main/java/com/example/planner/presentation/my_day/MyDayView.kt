package com.example.planner.presentation.my_day

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.AddToEndSingle

interface MyDayView: MvpView {
    @AddToEndSingle
    fun showAnimationFab(offset: Float)

    @AddToEnd
    fun showAddEventPopupDialog()
}