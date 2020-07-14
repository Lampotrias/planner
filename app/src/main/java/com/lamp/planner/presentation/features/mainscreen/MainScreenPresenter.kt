package com.lamp.planner.presentation.features.mainscreen

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.lamp.planner.R
import com.lamp.planner.domain.Group
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.domain.interactors.CreateGroupInteractor
import com.lamp.planner.domain.interactors.GetGroupsInteractor
import com.lamp.planner.domain.interactors.GlobalInteractor
import com.lamp.planner.domain.interactors.None
import com.lamp.planner.domain.interactors.SetDefaultGroupInteractor
import com.lamp.planner.presentation.base.BasePresenter
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class MainScreenPresenter @Inject constructor(
    private val globalInteractor: GlobalInteractor,
    private val getGroupsInteractor: GetGroupsInteractor,
    private val createGroupInteractor: CreateGroupInteractor,
    private val setDefaultGroupInteractor: SetDefaultGroupInteractor
) : BasePresenter<MainScreenView>() {
    private var bSelectMode = false
    private val selectedGroup = mutableListOf<Long>()

    override fun onFirstViewAttach() {
        initMainScreen()
        initAuthorize()
        super.onFirstViewAttach()
    }

    private fun initAuthorize() {
        viewState.initAuthorizeForm()
    }

    private fun initMainScreen() {
        getGroups()
    }

    fun setOffset(offset: Float) {
        globalInteractor.setOffset(offset)
    }

    fun setSlidingState(state: Int) {
        globalInteractor.setSlidingState(state)
    }

    fun clickAuthButton() {
        val destination = MainScreenFragmentDirections.actionMainScreenFragmentToAuthFragment()
        viewState.navigateAuth(destination)
    }

    fun clickBtnMyDay() {
        viewState.navigateMyDay(R.id.action_global_my_day)
    }

    private fun getGroups() {
        setDefaultGroupInteractor(1L) { it.fold(::handleFailure) {} }
        val successExec: (List<Group>) -> Unit = {
//            if (it.isEmpty()) {
//                val group1 = Group(0L, "Личное", R.drawable.group_default, "#000000", 999, true)
//                val group2 = Group(0L, "Работа", R.drawable.group_work, "#000000", 998, false)
//                createGroupInteractor(group1) { id -> id.fold(viewState::handleFailure, {}) }
//                createGroupInteractor(group2) { id -> id.fold(viewState::handleFailure, {}) }
//                getGroups()
//            }
            viewState.showGroups(it)
        }
        getGroupsInteractor(None()) { it.fold(viewState::handleFailure, successExec) }
    }

    private fun handleFailure(failure: Failure) {
        viewState.handleFailure(failure)
    }

    fun setUserAccount(account: GoogleSignInAccount) {
        viewState.setAccountCation(account.displayName.toString())
    }

    fun clickCreateGroup() {
        val navDirections =
            MainScreenFragmentDirections.actionMainScreenFragmentToCreateGroupDialog()
        viewState.navigateCreateGroupDialog(navDirections)
    }

    fun processSaveGroup(groupName: String, groupLogo: Int) {
        val group = Group(0L, groupName, groupLogo, "#000000", Group.DEFAULT_SORT, true)
        createGroupInteractor(group) { id -> id.fold(viewState::handleFailure) { getGroups() } }
    }

    fun clickGroup(group: Group, position: Int) {
        if (bSelectMode) {
            if (selectedGroup.contains(group.id)) {
                viewState.deactivateGroup(position)
                selectedGroup.remove(group.id)
            } else {
                viewState.activateGroup(position)
                selectedGroup.add(group.id)
            }
            updateSelectedCounter()
        }
    }

    fun clickLongGroup(group: Group, position: Int) {
        if (!bSelectMode) {
            selectedGroup.add(group.id)
            viewState.showGroupEditDialog()
            viewState.activateGroup(position)
            updateSelectedCounter()
            bSelectMode = true
        }
    }

    private fun updateSelectedCounter() {
        if (selectedGroup.isEmpty()) {
            bSelectMode = false
            viewState.hideGroupEditDialog()
        } else {
            viewState.setSelectValueInBottom(selectedGroup.size)
        }
    }

    fun clickPalette() {
        TODO("Not yet implemented")
    }

    fun clickImage() {
        TODO("Not yet implemented")
    }

    fun clickEdit() {
        TODO("Not yet implemented")
    }

    fun clickDelete() {
        TODO("Not yet implemented")
    }

    fun clickBookmark() {
        TODO("Not yet implemented")
    }
}
