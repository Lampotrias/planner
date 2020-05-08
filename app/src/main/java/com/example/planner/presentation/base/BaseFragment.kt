package com.example.planner.presentation.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import timber.log.Timber

abstract class BaseFragment: Fragment() {
    abstract fun onInitDependencyInjection()

    override fun onAttach(context: Context) {
        Timber.i("onAttach")
        super.onAttach(context)
        onInitDependencyInjection()
    }

    fun requireCompatActivity(): AppCompatActivity {
        requireActivity()
        val activity = requireActivity()
        if (activity is AppCompatActivity) {
            return activity
        } else {
            throw TypeCastException("Main activity should extend from 'AppCompatActivity'")
        }
    }
}