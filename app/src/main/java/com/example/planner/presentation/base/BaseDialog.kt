package com.example.planner.presentation.base

import android.graphics.Point
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.planner.AndroidApp
import com.example.planner.domain.excetion.Failure
import moxy.MvpDelegate
import moxy.MvpDelegateHolder

abstract class BaseDialog : DialogFragment(), MvpDelegateHolder {
    private val mvpDelegate: MvpDelegate<out BaseDialog> = MvpDelegate(this)
    protected val appContext by lazy { (requireActivity().applicationContext as AndroidApp).getComponent() }
    private var stateSaved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        onInitDependencyInjection()
        super.onCreate(savedInstanceState)
        getMvpDelegate().onCreate(savedInstanceState)
    }

    protected fun get90Width(): Int {
        var width = WindowManager.LayoutParams.WRAP_CONTENT
        val size = Point()
        dialog?.context?.display?.getRealSize(size).let {
            width = (size.x * 0.9).toInt()
        }
        return width
    }

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
}
