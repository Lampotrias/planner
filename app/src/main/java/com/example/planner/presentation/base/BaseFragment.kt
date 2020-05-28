package com.example.planner.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.planner.AndroidApp
import com.example.planner.R
import com.example.planner.databinding.ActivityMainBinding.inflate
import com.example.planner.databinding.MyDayBinding
import com.example.planner.di.AppComponent
import com.example.planner.domain.excetion.Failure
import moxy.MvpAppCompatFragment
import timber.log.Timber

abstract class BaseFragment: MvpAppCompatFragment() {
    val appComponent: AppComponent by lazy {
        (requireContext().applicationContext as AndroidApp).getComponent()
    }

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

    fun hFailure(failure: Failure?) {
        when (failure) {
            is Failure.ServerError -> notify("Error server connect");
            is Failure.DatabaseErrorQuery -> notify("Error database query");
        }
    }

    private fun notify(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}