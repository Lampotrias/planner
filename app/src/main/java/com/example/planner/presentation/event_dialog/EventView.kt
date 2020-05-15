package com.example.planner.presentation.event_dialog

import moxy.MvpView
import moxy.viewstate.strategy.alias.Skip

interface EventView: MvpView {
    @Skip
    fun close()
}