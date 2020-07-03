package com.lamp.planner.presentation.features.mainscreen

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.lamp.planner.R
import com.lamp.planner.domain.Group
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.domain.interactors.*
import com.lamp.planner.presentation.adapters.GroupDelegateAdapter
import com.lamp.planner.presentation.adapters.ManagerImpl
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
            if (it.isEmpty()) {
                val group1 = Group(0L, "Личное", R.drawable.group_default, "#000000", 999, true)
                val group2 = Group(0L, "Работа", R.drawable.group_work, "#000000", 998, false)
                createGroupInteractor(group1) { id -> id.fold(viewState::handleFailure, {}) }
                createGroupInteractor(group2) { id -> id.fold(viewState::handleFailure, {}) }
                getGroups()
            }
            val manager = ManagerImpl<Group>()
            manager.addDelegate(GroupDelegateAdapter())
            viewState.showGroups(manager, it)
        }
        getGroupsInteractor(None()) { it.fold(viewState::handleFailure, successExec) }
    }

    private fun handleFailure(failure: Failure) {
        viewState.handleFailure(failure)
    }

    fun setUserAccount(account: GoogleSignInAccount) {
        viewState.setAccountCation(account.displayName.toString())
    }

    fun clickGroup(group: Group) {
        viewState.showMessage("click")
    }

    fun clickLongGroup(group: Group) {
        viewState.showMessage("Long click")
    }
}
