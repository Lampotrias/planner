package com.example.planner.presentation.features.main_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.example.planner.AndroidApp
import com.example.planner.R
import com.example.planner.databinding.MainScreenBinding
import com.example.planner.domain.Group
import com.example.planner.domain.excetion.Failure
import com.example.planner.domain.interactors.GlobalInteractor
import com.example.planner.presentation.base.BaseFragment
import com.example.planner.presentation.features.main_screen.adapter.GroupListAdapter
import com.example.planner.presentation.features.main_screen.di.DaggerMainScreenComponent
import com.example.planner.presentation.features.main_screen.di.MainScreenComponent
import com.google.android.material.bottomsheet.BottomSheetBehavior
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject
import javax.inject.Provider

open class MainScreenFragment : BaseFragment(),
    MainScreenView {

    @Inject
    lateinit var presenterProvider: MainScreenPresenter
    private val mPresenter by moxyPresenter { presenterProvider }

    override fun onCreate(savedInstanceState: Bundle?) {
        onInitDependencyInjection()
        super.onCreate(savedInstanceState)
    }

    private lateinit var binding: MainScreenBinding
    private lateinit var sheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun handleFailure(failure: Failure?) {
        prepareFailure(failure)
    }

    override fun showGroups(groups: List<Group>) {
        binding.groupList.apply {
            adapter = GroupListAdapter(groups)
            layoutManager = GridLayoutManager(requireContext(), 5)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainScreenBinding.inflate(inflater, container, false)

        binding.btnMyDay.setOnClickListener { mPresenter.clickBtnMyDay() }
        binding.btnMyDay.setImageResource(R.drawable.my_day_icon)


        initBottom()
        return binding.root
    }

    private fun initBottom() {
        sheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                mPresenter.setOffset(slideOffset)
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                mPresenter.setSlidingState(newState)
            }

        })
    }

    override fun showMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun openBottomSheet() {
        sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onInitDependencyInjection() {
        DaggerMainScreenComponent
            .builder()
            .appComponent((requireContext().applicationContext as AndroidApp).getComponent())
            .build().inject(this)
    }
}