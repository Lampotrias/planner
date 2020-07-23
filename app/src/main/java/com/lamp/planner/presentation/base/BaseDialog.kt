package com.lamp.planner.presentation.base

import android.os.Bundle
import android.util.Size
import android.util.SizeF
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.lamp.planner.AndroidApp
import com.lamp.planner.R
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

        calculateSize(setSizeDialog()).also {
            dialog?.window?.setLayout(
                it.width,
                it.height
            )
        }
    }

    abstract fun setSizeDialog(): SizeF

    private fun calculateSize(rawSizeF: SizeF): Size {
        val prepareSize: (Int) -> Int = rt@{
            return@rt when (it) {
                0 -> WindowManager.LayoutParams.WRAP_CONTENT
                1 -> WindowManager.LayoutParams.MATCH_PARENT
                else -> it
            }
        }
        val width = (resources.displayMetrics.widthPixels * rawSizeF.width).toInt()
        val height = (resources.displayMetrics.heightPixels * rawSizeF.height).toInt()
        return Size(prepareSize(width), prepareSize(height))
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
