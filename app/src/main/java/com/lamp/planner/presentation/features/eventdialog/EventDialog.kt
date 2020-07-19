package com.lamp.planner.presentation.features.eventdialog

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.setFragmentResult
import androidx.navigation.NavDirections
import androidx.navigation.fragment.navArgs
import com.lamp.planner.R
import com.lamp.planner.databinding.AddEventFragmentBinding
import com.lamp.planner.domain.Group
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.extention.navigate
import com.lamp.planner.presentation.base.BaseDialog
import com.lamp.planner.presentation.features.calendardetail.CalendarDialog
import com.lamp.planner.presentation.features.calendardetail.CalendarDialog.Companion.CALENDAR_DIALOG_RESULT_EVENT_OBJ
import com.lamp.planner.presentation.features.eventdialog.di.DaggerEventDialogComponent
import com.lamp.planner.presentation.features.grouplistdialog.GroupListDialog
import com.lamp.planner.presentation.features.grouplistdialog.GroupListDialog.Companion.GROUPS_DIALOG_PARAM_OBJ
import com.lamp.planner.presentation.features.myday.EventTransferObject
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
            CalendarDialog.CALENDAR_DIALOG_REQUEST_KEY,
            this,
            FragmentResultListener { _: String, result: Bundle ->
                val transferObject =
                    result.getParcelable<EventTransferObject>(CALENDAR_DIALOG_RESULT_EVENT_OBJ)
                transferObject?.let { mPresenter.setArgsFromCalendar(it) }
                this.dialog?.show()
            })

        // Back stack from groups
        parentFragmentManager.setFragmentResultListener(
            GroupListDialog.GROUPS_DIALOG_REQUEST_KEY,
            this,
            FragmentResultListener { _, result ->
                val group = result.getParcelable<Group>(GROUPS_DIALOG_PARAM_OBJ)
                group?.let { mPresenter.setArgsFromGroupsDialog(it) }
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

            timeEvent.setOnClickListener { mPresenter.onTimeClick() }

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

    override fun onInitDependencyInjection() {
        DaggerEventDialogComponent.builder()
            .appComponent(appComponent)
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

    override fun showFormattedTime(
        strDate: String,
        strTime: String,
        bAllDay: Int
    ) {
        binding.timeEvent.text =
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

    override fun setEnableSubmit(enable: Boolean) {
        binding.submitButton.apply {
            setOnClickListener { if (enable) mPresenter.clickSubmit() }
            alpha = if (enable) 1f else 0.2f
        }
    }

    override fun setGroupFormCaption(groupName: String) {
        binding.groupEvent.text = groupName
    }

    override fun handleFailure(failure: Failure?) {
        notify(prepareFailure(failure))
    }
}
