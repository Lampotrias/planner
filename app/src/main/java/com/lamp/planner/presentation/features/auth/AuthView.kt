package com.lamp.planner.presentation.features.auth

import com.lamp.planner.domain.excetion.Failure
import moxy.MvpView
import moxy.viewstate.strategy.alias.Skip

interface AuthView : MvpView {
    @Skip
    fun closeAuthFragmentWithNotify(message: String)

    @Skip
    fun handleError(failure: Failure)
}
