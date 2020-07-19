package com.lamp.planner.presentation.base

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.lamp.planner.AndroidApp
import com.lamp.planner.R
import com.lamp.planner.domain.Constants
import com.lamp.planner.domain.DateToText
import com.lamp.planner.domain.excetion.Failure
import moxy.MvpDelegate
import moxy.MvpDelegateHolder

abstract class BaseDialog : DialogFragment(), MvpDelegateHolder {
    private val mvpDelegate: MvpDelegate<out BaseDialog> = MvpDelegate(this)
    protected val appComponent by lazy { (requireActivity().applicationContext as AndroidApp).getComponent() }
    private var stateSaved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        onInitDependencyInjection()
        super.onCreate(savedInstanceState)
        getMvpDelegate().onCreate(savedInstanceState)
    }

    private fun get90Width() =
        (resources.displayMetrics.widthPixels * Constants.WIDTH_DIALOG_FLOAT).toInt()

    override fun onResume() {
        super.onResume()
        stateSaved = false
        getMvpDelegate().onAttach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        stateSaved = true
        getMvpDelegate().onSaveInstanceState(outState)
        getMvpDelegate().onDetach()
    }

    override fun onStop() {
        super.onStop()
        getMvpDelegate().onDetach()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_rounded_bg)

        dialog?.window?.setLayout(
            get90Width(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        getMvpDelegate().onDetach()
        getMvpDelegate().onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (stateSaved) {
            stateSaved = false
            return
        }
        var anyParentIsRemoving = false
        var parent: Fragment? = parentFragment
        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving
            parent = parent.parentFragment
        }

        if (isRemoving || anyParentIsRemoving) {
            getMvpDelegate().onDestroy()
        }
    }

    override fun getMvpDelegate() = mvpDelegate

    abstract fun onInitDependencyInjection()

    fun prepareFailure(failure: Failure?): String {
        return when (failure) {
            is Failure.ServerError -> "Error server connect"
            is Failure.DatabaseErrorQuery -> "Error database query ${failure.message}"
            else -> "Unknown error"
        }
    }

    fun notify(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun getFormattedDate(date: String): String {
        return when (date) {
            DateToText.Today.name -> resources.getString(R.string.today)
            DateToText.Yesterday.name -> resources.getString(R.string.yesterday)
            DateToText.Tomorrow.name -> resources.getString(R.string.tomorrow)
            else -> date
        }
    }
}
