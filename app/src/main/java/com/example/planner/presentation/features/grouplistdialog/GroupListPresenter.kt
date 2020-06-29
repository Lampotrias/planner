package com.example.planner.presentation.features.grouplistdialog

import com.example.planner.domain.Group
import com.example.planner.domain.excetion.Failure
import com.example.planner.domain.interactors.GetGroupsInteractor
import com.example.planner.domain.interactors.None
import com.example.planner.presentation.adapters.ManagerImpl
import com.example.planner.presentation.base.BasePresenter
import com.example.planner.presentation.features.main_screen.adapter.GroupDelegateAdapter
import javax.inject.Inject

class GroupListPresenter @Inject constructor(
    val getGroupsInteractor: GetGroupsInteractor
) : BasePresenter<GroupListView>() {

    fun setInputNavArgs(groupListDialogArgs: GroupListDialogArgs) {
        if (groupListDialogArgs.groupList.isNotEmpty() && (groupListDialogArgs.selectedId > 0)
        ) {
            showGroups(groupListDialogArgs.groupList.toList(), groupListDialogArgs.selectedId)
        } else {
            val success: (List<Group>) -> Unit = { groups ->
                val group = groups.first { it.default }
                showGroups(groups, group.id)
            }
            getGroupsInteractor(None()) { it.fold(this::handleError, success) }
        }
    }

    private fun showGroups(groups: List<Group>, selectedId: Long) {
        val manager = ManagerImpl<Group>()
        var position = 0
        manager.addDelegate(GroupDelegateAdapter())

        groups.forEachIndexed { index, group ->
            if (group.id == selectedId) {
                position = index
            }
        }
        viewState.showGroups(manager, groups, position)
    }

    private fun handleError(failure: Failure) {
        viewState.handleFailure(failure)
    }

    fun clickOnGroup(group: Group) {
        viewState.close(group)
    }
}
