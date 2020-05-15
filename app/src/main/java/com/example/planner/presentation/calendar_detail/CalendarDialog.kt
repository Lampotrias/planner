package com.example.planner.presentation.calendar_detail

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.setFragmentResult
import com.example.planner.AndroidApp
import com.example.planner.databinding.CalendarFragmentBinding
import com.example.planner.presentation.base.BaseDialog
import com.example.planner.presentation.calendar_detail.di.DaggerCalendarComponent

import moxy.ktx.moxyPresenter
import timber.log.Timber
import javax.inject.Inject

class CalendarDialog @Inject constructor() : BaseDialog(), CalendarView{

    private lateinit var binding: CalendarFragmentBinding

    @Inject
    lateinit var presenterProvider: CalendarPresenter
    private val mPresenter by moxyPresenter { presenterProvider }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        onInitDependencyInjection()
        super.onCreate(savedInstanceState)
        parentFragmentManager.setFragmentResultListener("requestToCalendar", this, FragmentResultListener{
                _: String, result: Bundle ->
            val value = result["key"]
            Timber.e(value.toString())
        })
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
        setFragmentResult("responseToCalendar", bundleOf("key" to "value"))
        this.dialog?.dismiss()
    }

}