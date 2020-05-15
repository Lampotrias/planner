package com.example.planner.presentation.event_dialog

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.setFragmentResult
import com.example.planner.AndroidApp
import com.example.planner.databinding.AddEventFragmentBinding
import com.example.planner.presentation.base.BaseDialog
import com.example.planner.presentation.calendar_detail.CalendarDialog
import com.example.planner.presentation.event_dialog.di.DaggerEventDialogComponent

import moxy.ktx.moxyPresenter
import timber.log.Timber
import javax.inject.Inject

class EventDialog @Inject constructor() : BaseDialog(), EventView {

    private lateinit var binding: AddEventFragmentBinding

    @Inject
    lateinit var presenterProvider: EventPresenter
    private val mPresenter by moxyPresenter { presenterProvider }

    override fun onCreate(savedInstanceState: Bundle?) {
        onInitDependencyInjection()
        super.onCreate(savedInstanceState)

        parentFragmentManager.setFragmentResultListener("responseToCalendar", this, FragmentResultListener{
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
        binding = AddEventFragmentBinding.inflate(inflater, container, false)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)


        binding.submitButton.setOnClickListener{
            setFragmentResult("requestToEvent", bundleOf("key" to "value1"))
            mPresenter.Log("sss")
        }

        binding.customIcon.setOnClickListener{

        }

        binding.timeEvent.setOnClickListener{
            val calendarDialog = CalendarDialog()
            setFragmentResult("requestToCalendar", bundleOf("key" to "value"))
            calendarDialog.show(parentFragmentManager, "calendar")
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
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