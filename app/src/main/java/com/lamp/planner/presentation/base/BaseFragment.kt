package com.lamp.planner.presentation.base

import android.content.Context
import android.widget.Toast
import androidx.annotation.LayoutRes
import com.lamp.planner.domain.excetion.Failure
import moxy.MvpAppCompatFragment
import timber.log.Timber

abstract class BaseFragment(@LayoutRes layoutId: Int) : MvpAppCompatFragment(layoutId) {
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
    }
}
