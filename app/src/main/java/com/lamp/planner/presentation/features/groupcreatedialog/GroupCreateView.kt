package com.lamp.planner.presentation.features.groupcreatedialog

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.Skip

interface GroupCreateView : MvpView {
    @Skip
    fun close()

    @AddToEnd
    fun setEnableSubmit(enable: Boolean)
}
