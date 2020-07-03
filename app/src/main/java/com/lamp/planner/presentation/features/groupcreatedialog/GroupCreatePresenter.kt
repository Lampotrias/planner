package com.lamp.planner.presentation.features.groupcreatedialog

import com.lamp.planner.presentation.base.BasePresenter
import javax.inject.Inject

class GroupCreatePresenter @Inject constructor() : BasePresenter<GroupCreateView>() {
    fun actionSubmit() {
        viewState.close()
    }

    fun setTextInNameField(name: String) {
        viewState.setEnableSubmit(name.isNotEmpty())
    }
}
