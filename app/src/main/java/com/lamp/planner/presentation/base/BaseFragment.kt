package com.lamp.planner.presentation.base

import android.content.Context
import android.widget.Toast
import com.lamp.planner.AndroidApp
import com.lamp.planner.di.AppComponent
import com.lamp.planner.domain.excetion.Failure
import moxy.MvpAppCompatFragment
import timber.log.Timber

abstract class BaseFragment : MvpAppCompatFragment() {
    val appComponent: AppComponent by lazy {
        (requireContext().applicationContext as AndroidApp).getComponent()
    }

    abstract fun onInitDependencyInjection()

    fun prepareFailure(failure: Failure?): String {
        return when (failure) {
            is Failure.ServerError -> "Error server connect"
            is Failure.DatabaseErrorQuery -> "Error database query ${failure.message}"
            is Failure.AuthorizeError -> "Error authorize  (${failure.message})"
            else -> "Unknown error"
        }
    }

    fun notify(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context) {
        Timber.i("onAttach")
        super.onAttach(context)
        onInitDependencyInjection()
    }
}
