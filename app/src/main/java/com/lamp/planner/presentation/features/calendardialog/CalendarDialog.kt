package com.lamp.planner.presentation.features.calendardialog

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.SizeF
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.lamp.planner.R
import com.lamp.planner.databinding.DialogCalendarBinding
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.presentation.base.BaseDialog
import com.lamp.planner.presentation.features.myday.EventTransferObject
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class CalendarDialog @Inject constructor() : BaseDialog(),
    CalendarView {
    private val binding by viewBinding {
        DialogCalendarBinding.bind(it.requireView())
    }

    @Inject
    lateinit var presenterProvider: CalendarPresenter
    private val mPresenter by moxyPresenter { presenterProvider }
    private val args: CalendarDialogArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submitButton.setOnClickListener { mPresenter.clickSubmit() }
        binding.calendar.setOnDateChangeListener { _, year, month, day ->
            mPresenter.setDate(
                year,
                month,
                day
            )
        }
        mPresenter.setInputNavArgs(args)
    }

    companion object {
        const val CALENDAR_DIALOG_REQUEST_KEY = "CALENDAR_DIALOG_REQUEST_KEY"
        const val CALENDAR_DIALOG_RESULT_EVENT_OBJ = "CALENDAR_DIALOG_RESULT_EVENT_OBJ"
    }

    override fun setSizeDialog(): SizeF = SizeF(0.9f, 0f)

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
