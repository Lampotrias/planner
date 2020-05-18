package com.example.planner.presentation.event_dialog

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.setFragmentResult
import androidx.navigation.NavDirections
import androidx.navigation.fragment.navArgs
import com.example.planner.AndroidApp
import com.example.planner.databinding.AddEventFragmentBinding
import com.example.planner.extention.navigate
import com.example.planner.presentation.base.BaseDialog
import com.example.planner.presentation.calendar_detail.CalendarDialog
import com.example.planner.presentation.calendar_detail.CalendarDialog.Companion.CALENDAR_DIALOG_PARAM_OBJ
import com.example.planner.presentation.event_dialog.di.DaggerEventDialogComponent
import com.example.planner.presentation.my_day.EventTransferObject

import moxy.ktx.moxyPresenter
import javax.inject.Inject

class EventDialog @Inject constructor() : BaseDialog(), EventView {

    private lateinit var binding: AddEventFragmentBinding
    private val args: EventDialogArgs by navArgs()


    @Inject
    lateinit var presenterProvider: EventPresenter
    private val mPresenter by moxyPresenter { presenterProvider }


    companion object {
        const val EVENT_DIALOG_RESULT = "EVENT_DIALOG_RESULT"
        const val EVENT_DIALOG_PARAM_OBJ = "EVENT_DIALOG_PARAM_OBJ"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        onInitDependencyInjection()
        super.onCreate(savedInstanceState)

        //Back stack form calendar
        parentFragmentManager.setFragmentResultListener(
            CalendarDialog.CALENDAR_DIALOG_RESULT,
            this,
            FragmentResultListener { _: String, result: Bundle ->
                mPresenter.setArgsFromBackStack(result[CALENDAR_DIALOG_PARAM_OBJ] as EventTransferObject)
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.setInputNavArgs(args)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddEventFragmentBinding.inflate(inflater, container, false)

        setUserEvents()
        return binding.root
    }

    private fun setUserEvents() {
        binding.apply {

            customIcon.setOnClickListener {}

            timeEvent.text = "TODAY \n All day"
            timeEvent.setOnClickListener {
                mPresenter.onTimeClick()
//            val calendarDialog = CalendarDialog()
//            setFragmentResult(CalendarDialog.CALENDAR_DIALOG_REQUEST, bundleOf(CalendarDialog.CALENDAR_DIALOG_PARAM_OBJ to "CALENDAR_DIALOG_REQUEST"))
//            calendarDialog.show(parentFragmentManager, "calendar")
            }

            nameEvent.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {}
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    mPresenter.setTextInNameField(nameEvent.text.toString())
                }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun onInitDependencyInjection() {
        DaggerEventDialogComponent.builder()
            .appComponent((requireActivity().applicationContext as AndroidApp).getComponent())
            .build().inject(this)
    }

    override fun close(eventObj: EventTransferObject) {
        setFragmentResult(
            EVENT_DIALOG_RESULT,
            bundleOf(EVENT_DIALOG_PARAM_OBJ to eventObj)
        )
        this.dialog?.dismiss()
    }

    override fun showCalendarPopupDialog(navDirections: NavDirections) {
        this.navigate(navDirections)
    }

    override fun showFormattedTime(year: Int, month: Int, day: Int, hours: Int, minutes: Int) {
        binding.timeEvent.text = "$day-$month-$year \n $hours:$minutes"
    }

    override fun setEnableSubmit(enable: Boolean) {
        binding.submitButton.setOnClickListener { if (enable) mPresenter.clickSubmit() }
        binding.submitButton.alpha = if (enable) 1f else 0.2f

    }
}