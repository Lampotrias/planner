package com.lamp.planner.presentation.features.groupcreatedialog

import com.lamp.planner.domain.SimpleGroupFields
import com.lamp.planner.presentation.base.BasePresenter
import javax.inject.Inject

class GroupCreatePresenter @Inject constructor() : BasePresenter<GroupCreateView>() {
    private var groupFields = SimpleGroupFields(0L, "", 0, 0)
    fun actionSubmit() {
        viewState.close(groupFields)
    }

    fun setTextInNameField(name: String) {
        groupFields.name = name
        viewState.setEnableSubmit(name.isNotEmpty())
    }

    fun setInputNavArgs(args: CreateGroupDialogArgs) {
        args.groupParams?.let {
            groupFields = it
        }
        viewState.initDialog(groupFields.name, groupFields.image, groupFields.color)
    }
}
