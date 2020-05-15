package com.example.planner.presentation.main_screen

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.AddToEndSingle

interface MainScreenView: MvpView {
    @AddToEndSingle
    fun showMessage(message: String)

    @AddToEnd
    fun openBottomSheet()
}