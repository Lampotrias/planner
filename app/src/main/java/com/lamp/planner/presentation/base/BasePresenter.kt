package com.lamp.planner.presentation.base

import moxy.MvpPresenter
import moxy.MvpView

open class BasePresenter<View : MvpView> : MvpPresenter<View>()
