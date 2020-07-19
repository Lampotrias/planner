package com.lamp.planner.presentation.features.calendardetail

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.lamp.planner.R
import com.lamp.planner.databinding.CalendarFragmentBinding
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.presentation.base.BaseDialog
import com.lamp.planner.presentation.features.calendardetail.di.DaggerCalendarComponent
import com.lamp.planner.presentation.features.myday.EventTransferObject
import moxy.ktx.moxyPresenter
import java.util.*
import javax.inject.Inject

class CalendarDialog @Inject constructor() : BaseDialog(),
    CalendarView {

    private lateinit var binding: CalendarFragmentBinding
    private val args: CalendarDialogArgs by navArgs()

    @Inject
    lateinit var presenterProvider: CalendarPresenter
    private val mPresenter by moxyPresenter { presenterProvider }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.setInputNavArgs(args)
    }

    companion object {
        const val CALENDAR_DIALOG_REQUEST_KEY = "CALENDAR_DIALOG_REQUEST_KEY"
        const val CALENDAR_DIALOG_RESULT_EVENT_OBJ = "CALENDAR_DIALOG_RESULT_EVENT_OBJ"
    }

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

        binding.submitButton.setOnClickListener { mPresenter.clickSubmit() }
        binding.calendar.setOnDateChangeListener { _, year, month, day ->
            mPresenter.setDate(
                year,
                month,
                day
            )
        }

        return binding.root
    }

    override fun onInitDependencyInjection() {
        DaggerCalendarComponent.builder()
            .appComponent(appComponent)
            .build().inject(this)
    }

    override fun close(eventObj: EventTransferObject) {
        setFragmentResult(
            CALENDAR_DIALOG_REQUEST_KEY,
            bundleOf(CALENDAR_DIALOG_RESULT_EVENT_OBJ to eventObj)
        )
        dialog?.dismiss()
    }

    override fun showFormattedTime(
        strDate: String,
        strTime: String,
        bAllDay: Int
    ) {
        binding.textToTime.text =
            if (bAllDay > 0) resources.getString(
                R.string.dialog_format_time,
                getFormattedDate(strDate),
                resources.getString(R.string.all_day)
            )
            else resources.getString(
                R.string.dialog_format_time,
                getFormattedDate(strDate),
                strTime
            )
    }

    override fun initTimeControl(hours: Int, minutes: Int) {
        binding.textToTime.setOnClickListener {
            TimePickerDialog(
                requireContext(),
                TimePickerDialog.OnTimeSetListener { _, hours, minutes ->
                    mPresenter.setTime(
                        hours,
                        minutes
                    )
                }, hours, minutes, true
            )
                .show()
        }
    }

    override fun initCalendarControl(year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        binding.calendar.date = calendar.timeInMillis
    }

    override fun handleFailure(failure: Failure?) {
        notify(prepareFailure(failure))
    }
}
