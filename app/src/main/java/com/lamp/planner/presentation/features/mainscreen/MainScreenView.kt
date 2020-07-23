package com.lamp.planner.presentation.features.mainscreen

import android.os.Bundle
import androidx.navigation.NavDirections
import com.lamp.planner.domain.Group
import com.lamp.planner.domain.excetion.Failure
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

interface MainScreenView : MvpView {
    @AddToEndSingle
    fun showMessage(message: String)

    @AddToEnd
    fun navigateMyDay(resId: Int)

    @Skip
    fun handleFailure(failure: Failure?)

    @Skip
    fun navigateAuth(navDirections: NavDirections)

    @Skip
    fun navigateCreateUpdateGroupDialog(navDirections: NavDirections, params: Bundle? = null)

    @AddToEnd
    fun showGroups(groups: List<Group>)

    @AddToEnd
    fun initAuthorizeForm()

    @AddToEnd
    fun setAccountCation(name: String)

    @AddToEnd
    fun showGroupEditDialog()

    @AddToEnd
    fun hideGroupEditDialog()

    @AddToEnd
    fun setSelectValueInBottom(value: Int)

    @AddToEnd
    fun activateGroup(position: Int)

    @AddToEnd
    fun deactivateGroup(position: Int)

    @AddToEnd
    fun showPalette()

    @AddToEnd
    fun showImageSelector()
}
