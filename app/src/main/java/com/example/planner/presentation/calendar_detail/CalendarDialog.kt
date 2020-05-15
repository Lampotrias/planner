package com.example.planner.presentation.calendar_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.example.planner.AndroidApp
import com.example.planner.databinding.CalendarFragmentBinding
import com.example.planner.presentation.base.BaseDialog
import com.example.planner.presentation.calendar_detail.di.DaggerCalendarComponent

import moxy.ktx.moxyPresenter
import javax.inject.Inject

class CalendarDialog @Inject constructor() : BaseDialog(), CalendarView{

    private lateinit var binding: CalendarFragmentBinding

    @Inject
    lateinit var presenterProvider: CalendarPresenter
    private val mPresenter by moxyPresenter { presenterProvider }

    override fun onCreate(savedInstanceState: Bundle?) {
        onInitDependencyInjection()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CalendarFragmentBinding.inflate(inflater, container, false)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        binding.submitButton.setOnClickListener{mPresenter.Log("sss")}
        return binding.root
    }

    private fun onInitDependencyInjection() {
        DaggerCalendarComponent.builder()
            .appComponent((requireActivity().applicationContext as AndroidApp).getComponent())
            .build().inject(this)
    }

    override fun close() {
        this.dialog?.dismiss()
    }

}