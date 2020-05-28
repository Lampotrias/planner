package com.example.planner.presentation.event_dialog

import com.example.planner.presentation.base.BasePresenter
import com.example.planner.presentation.my_day.EventTransferObject
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class EventPresenter @Inject constructor(): BasePresenter<EventView>() {
    private lateinit var eventObj: EventTransferObject

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun setArgsFromBackStack(eventTransferObject: EventTransferObject){
        eventObj = eventTransferObject
        viewState.showFormattedTime(eventObj.year, eventObj.month, eventObj.day, eventObj.hours, eventObj.minutes)
        Timber.tag("pop_event").e(eventObj.toString())
    }

    fun setInputNavArgs(eventDialogArgs: EventDialogArgs){
        eventObj = eventDialogArgs.eventObj ?: getActualEventObject()
        Timber.tag("inp_event").e(eventObj.toString())
    }

    fun clickSubmit(){
        viewState.close(eventObj)
    }

    fun onTimeClick(){
        val directions = EventDialogDirections.actionEventDialogToCalendarDialog(eventObj)
        viewState.showCalendarPopupDialog(directions)
    }

    fun setTextInNameField(name: String){
        eventObj.name = name
        viewState.setEnableSubmit(name.isNotEmpty())
    }

    private fun getActualEventObject(): EventTransferObject {
        val calendar: Calendar = Calendar.getInstance()
        return EventTransferObject("",
            calendar.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.YEAR),
            9,0,"Today")
    }
}