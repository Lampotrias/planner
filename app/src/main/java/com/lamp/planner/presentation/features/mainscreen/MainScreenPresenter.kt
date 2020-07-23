package com.lamp.planner.presentation.features.mainscreen

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.lamp.planner.R
import com.lamp.planner.domain.Group
import com.lamp.planner.domain.GroupColor
import com.lamp.planner.domain.GroupImage
import com.lamp.planner.domain.SimpleGroupFields
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.domain.interactors.CreateGroupInteractor
import com.lamp.planner.domain.interactors.GetGroupInteractor
import com.lamp.planner.domain.interactors.GetGroupsInteractor
import com.lamp.planner.domain.interactors.GlobalInteractor
import com.lamp.planner.domain.interactors.GroupDeleteInteractor
import com.lamp.planner.domain.interactors.GroupUpdateColorInteractor
import com.lamp.planner.domain.interactors.GroupUpdatePictureInteractor
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
    private val setDefaultGroupInteractor: SetDefaultGroupInteractor,
    private val groupUpdateColorInteractor: GroupUpdateColorInteractor,
    private val groupUpdatePictureInteractor: GroupUpdatePictureInteractor,
    private val groupDeleteInteractor: GroupDeleteInteractor,
    private val getGroupInteractor: GetGroupInteractor
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
        val successExec: (List<Group>) -> Unit = { viewState.showGroups(it) }
        getGroupsInteractor(None()) { it.fold(::handleFailure, successExec) }
    }

    private fun handleFailure(failure: Failure) {
        viewState.handleFailure(failure)
    }

    fun setUserAccount(account: GoogleSignInAccount) {
        viewState.setAccountCation(account.displayName.toString())
    }

    fun clickCreateGroup() {
        val navDirections =
            MainScreenFragmentDirections.actionMainScreenFragmentToCreateGroupDialog(null)
        viewState.navigateCreateUpdateGroupDialog(navDirections)
    }

    fun clickEditGroup() {
        getGroupInteractor(selectedGroup.first()) {
            it.fold(this::handleFailure) { group ->
                val fields =
                    SimpleGroupFields(group.id, group.name, group.picture, group.color.toInt())
                val navDirections =
                    MainScreenFragmentDirections.actionMainScreenFragmentToCreateGroupDialog(fields)
                viewState.navigateCreateUpdateGroupDialog(navDirections)
            }
        }
    }

    fun processSaveGroup(fields: SimpleGroupFields) {
        val group = Group(
            fields.id,
            fields.name,
            fields.image,
            fields.color.toString(),
            Group.DEFAULT_SORT,
            false
        )
        createGroupInteractor(group) { it.fold(::handleFailure) { successHideBottomPanel() } }
    }

    private fun successHideBottomPanel() {
        getGroups()
        resetSelectMode()
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
        viewState.showPalette()
    }

    fun clickImage() {
        viewState.showImageSelector()
    }

    fun setColor(color: Int) {
        val lColors = mutableListOf<GroupColor>()
        selectedGroup.forEach { lColors.add(GroupColor(it, color.toString())) }
        groupUpdateColorInteractor(lColors) {
            it.fold(::handleFailure) { successHideBottomPanel() }
        }
    }

    fun clickDelete() {
        groupDeleteInteractor(selectedGroup) {
            it.fold(::handleFailure) { successHideBottomPanel() }
        }
    }

    private fun resetSelectMode() {
        bSelectMode = false
        viewState.hideGroupEditDialog()
        selectedGroup.clear()
    }

    fun clickSetDefault() {
        setDefaultGroupInteractor(selectedGroup.first()) {
            it.fold(::handleFailure) { successHideBottomPanel() }
        }
    }

    fun setImage(image: Int) {
        val iImages = mutableListOf<GroupImage>()
        selectedGroup.forEach { iImages.add(GroupImage(it, image)) }
        groupUpdatePictureInteractor(iImages) {
            it.fold(::handleFailure) { successHideBottomPanel() }
        }
    }
}
