package com.example.planner.presentation.features.event_dialog

import com.example.planner.domain.Group
import com.example.planner.domain.excetion.Failure
import com.example.planner.domain.interactors.GetGroupsInteractor
import com.example.planner.domain.interactors.None
import com.example.planner.presentation.base.BasePresenter
import com.example.planner.presentation.features.my_day.EventTransferObject
import java.util.*
import javax.inject.Inject

class EventPresenter @Inject constructor(
    val getGroupsInteractor: GetGroupsInteractor
) : BasePresenter<EventView>() {
    private lateinit var eventObj: EventTransferObject
    private lateinit var cacheGroups: List<Group>

    private fun drawDialog(event: EventTransferObject) {
        eventObj = event
        viewState.showFormattedTime(
            eventObj.year,
            eventObj.month,
            eventObj.day,
            eventObj.hours,
            eventObj.minutes
        )
        viewState.showGroups(eventObj.groupName)
    }

    private fun handleError(failure: Failure) {
        viewState.handleFailure(failure)
    }

    fun setArgsFromCalendar(eventTransferObject: EventTransferObject) {
        drawDialog(eventTransferObject)
    }

    fun setArgsFromGroupsDialog(group: Group) {
        eventObj.apply {
            groupId = group.id
            groupName = group.name
        }
        drawDialog(eventObj)
    }

    fun setInputNavArgs(eventDialogArgs: EventDialogArgs) {
        if (eventDialogArgs.eventObj != null) {
            drawDialog(eventDialogArgs.eventObj)
        } else {
            val success: (List<Group>) -> Unit = { groups ->
                cacheGroups = groups
                val group = groups.first { it.default }
                drawDialog(getActualEventObject(group))
            }
            getGroupsInteractor(None()) { it.fold(this::handleError, success) }
        }
    }

    fun clickSubmit() {
        viewState.close(eventObj)
    }

    fun onTimeClick() {
        val directions =
            EventDialogDirections.actionEventDialogToCalendarDialog(
                eventObj
            )
        viewState.showCalendarPopupDialog(directions)
    }

    fun onGroupSelectClick() {
        val directions = EventDialogDirections.actionEventDialogToGroupListDialog2(
            cacheGroups.toTypedArray(),
            eventObj.groupId
        )
        viewState.showGroupsPopupDialog(directions)
    }

    fun setTextInNameField(name: String) {
        eventObj.name = name
        viewState.setEnableSubmit(name.isNotEmpty())
    }

    private fun getActualEventObject(group: Group): EventTransferObject {
        val calendar: Calendar = Calendar.getInstance()
        return EventTransferObject(
            "",
            group.id,
            group.name,
            calendar.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.YEAR), 9, 0, "Today"
        )
    }
}