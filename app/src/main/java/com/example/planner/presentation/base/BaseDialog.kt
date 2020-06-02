package com.example.planner.presentation.base

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.planner.presentation.features.calendar_detail.CalendarDialog
import moxy.MvpDelegate
import moxy.MvpDelegateHolder

abstract class BaseDialog:  DialogFragment(), MvpDelegateHolder {
    private val mvpDelegate: MvpDelegate<out BaseDialog> = MvpDelegate(this)
    private var stateSaved = false

    override fun onCreate(savedInstanceState: Bundle?) {
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
        stateSaved = true;
        getMvpDelegate().onSaveInstanceState(outState);
        getMvpDelegate().onDetach();
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
            stateSaved = false;
            return;
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
}