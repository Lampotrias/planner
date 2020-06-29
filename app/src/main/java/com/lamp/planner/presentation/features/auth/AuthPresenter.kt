package com.lamp.planner.presentation.features.auth

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.presentation.base.BasePresenter
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class AuthPresenter @Inject constructor() : BasePresenter<AuthView>() {

    fun handleSignInResult(task: Task<GoogleSignInAccount>?) = try {
        val account = task?.getResult(ApiException::class.java)
        viewState.closeAuthFragmentWithNotify(account?.displayName.toString())
    } catch (e: ApiException) {
        viewState.handleError(Failure.AuthorizeError(e.message))
    }
}
