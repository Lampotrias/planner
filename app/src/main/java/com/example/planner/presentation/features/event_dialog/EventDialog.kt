package com.example.planner.presentation.features.event_dialog

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.setFragmentResult
import androidx.navigation.NavDirections
import androidx.navigation.fragment.navArgs
import com.example.planner.AndroidApp
import com.example.planner.R
import com.example.planner.databinding.AddEventFragmentBinding
import com.example.planner.domain.Group
import com.example.planner.domain.excetion.Failure
import com.example.planner.extention.navigate
import com.example.planner.presentation.base.BaseDialog
import com.example.planner.presentation.features.calendar_detail.CalendarDialog
import com.example.planner.presentation.features.calendar_detail.CalendarDialog.Companion.CALENDAR_DIALOG_PARAM_OBJ
import com.example.planner.presentation.features.event_dialog.di.DaggerEventDialogComponent
import com.example.planner.presentation.features.group_list_dialog.GroupListDialog
import com.example.planner.presentation.features.my_day.EventTransferObject
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class EventDialog @Inject constructor() : BaseDialog(),
    EventView {

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
        super.onCreate(savedInstanceState)
        // in parent
        mPresenter.setInputNavArgs(args)
        // Back stack from calendar
        parentFragmentManager.setFragmentResultListener(
            CalendarDialog.CALENDAR_DIALOG_RESULT,
            this,
            FragmentResultListener { _: String, result: Bundle ->
                mPresenter.setArgsFromCalendar(result[CALENDAR_DIALOG_PARAM_OBJ] as EventTransferObject)
                this.dialog?.show()
            })

        // Back stack from groups
        parentFragmentManager.setFragmentResultListener(
            GroupListDialog.GROUPS_DIALOG_RESULT,
            this,
            FragmentResultListener { _, result ->
                mPresenter.setArgsFromGroupsDialog(result[GroupListDialog.GROUPS_DIALOG_PARAM_OBJ] as Group)
                this.dialog?.show()
            }
        )
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

            timeEvent.text = getString(R.string.dialog_event_format_time)
            timeEvent.setOnClickListener {
                mPresenter.onTimeClick()
            }

            groupEvent.setOnClickListener { mPresenter.onGroupSelectClick() }

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
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onInitDependencyInjection() {
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
        this.dialog?.hide()
    }

    override fun showGroupsPopupDialog(navDirections: NavDirections) {
        this.navigate(navDirections)
        this.dialog?.hide()
    }

    override fun showFormattedTime(year: Int, month: Int, day: Int, hours: Int, minutes: Int) {
        binding.timeEvent.text =
            getString(R.string.format_date_time, day, month, year, hours, minutes)
    }

    override fun setEnableSubmit(enable: Boolean) {
        binding.submitButton.setOnClickListener { if (enable) mPresenter.clickSubmit() }
        binding.submitButton.alpha = if (enable) 1f else 0.2f
    }

    override fun showGroups(groupName: String) {
        binding.groupEvent.text = groupName
    }

    override fun handleFailure(failure: Failure?) {
    }
}
