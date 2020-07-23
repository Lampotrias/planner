package com.lamp.planner.presentation.features.groupcreatedialog

import com.lamp.planner.domain.SimpleGroupFields
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.Skip

interface GroupCreateView : MvpView {
    @Skip
    fun close(simpleGroupFields: SimpleGroupFields)

    @AddToEnd
    fun setEnableSubmit(enable: Boolean)

    @AddToEnd
    fun initDialog(name: String, image: Int, color: Int)
}
