package com.example.planner.presentation.main_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.planner.AndroidApp
import com.example.planner.R
import com.example.planner.databinding.MainScreenFragmentBinding
import com.example.planner.presentation.base.BaseFragment
import com.example.planner.presentation.base.ViewModelFactory
import com.example.planner.presentation.main_screen.di.DaggerMainScreenComponent
import com.example.planner.presentation.my_day.MayDayViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import javax.inject.Inject

class MainScreenFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory<MayDayViewModel>

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainScreenFragmentBinding
    private lateinit var sheetBehavior: BottomSheetBehavior<ConstraintLayout>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_screen_fragment, container, false)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        initBottom()
        return binding.root
    }

    private fun initBottom() {
        sheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        sheetBehavior.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                viewModel.offset
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                viewModel.state
            }

        })
    }

    override fun onInitDependencyInjection() {
        DaggerMainScreenComponent.builder().appComponent((requireContext().applicationContext as AndroidApp).getComponent()).build()
    }
}