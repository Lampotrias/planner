package com.example.planner.presentation.event_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.example.planner.AndroidApp
import com.example.planner.databinding.AddEventFragmentBinding
import com.example.planner.presentation.base.BaseDialog
import com.example.planner.presentation.calendar_detail.CalendarDialog
import com.example.planner.presentation.event_dialog.di.DaggerEventDialogComponent

import moxy.ktx.moxyPresenter
import javax.inject.Inject

class EventDialog @Inject constructor() : BaseDialog(), EventView {

    private lateinit var binding: AddEventFragmentBinding

    @Inject
    lateinit var presenterProvider: EventPresenter
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
        binding = AddEventFragmentBinding.inflate(inflater, container, false)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        binding.submitButton.setOnClickListener{
            mPresenter.Log("sss")
        }

        binding.customIcon.setOnClickListener{

        }

        binding.timeEvent.setOnClickListener{
            val calendarDialog = CalendarDialog()
            calendarDialog.show(childFragmentManager, "calendar")
        }

        return binding.root
    }

    private fun onInitDependencyInjection() {
        DaggerEventDialogComponent.builder()
            .appComponent((requireActivity().applicationContext as AndroidApp).getComponent())
            .build().inject(this)
    }

    override fun close() {
        this.dialog?.dismiss()
    }

}