package com.example.planner.presentation.calendar_detail

import com.example.planner.presentation.base.BasePresenter
import com.example.planner.presentation.my_day.EventTransferObject
import timber.log.Timber
import javax.inject.Inject

class CalendarPresenter @Inject constructor(): BasePresenter<CalendarView>() {
    private lateinit var eventObj: EventTransferObject

    fun clickSubmit(){
        viewState.close(eventObj)
    }
    fun setInputNavArgs(calendarDialogArgs: CalendarDialogArgs){
        eventObj = calendarDialogArgs.eventObj
        setTimeControl()
        setCalendarControl()
        Timber.tag("inp_cal").e(eventObj.toString())
    }

    private fun setCalendarControl() {
        viewState.initCalendarControl(eventObj.year, eventObj.month, eventObj.day)
    }

    fun setDate(year: Int, month: Int, day: Int) {
        eventObj.year = year
        eventObj.month = month
        eventObj.day = day
        updateDateTimeArea()
    }

    fun setTime(hours: Int, minutes: Int){
        eventObj.hours = hours
        eventObj.minutes = minutes
        setTimeControl()
    }

    private fun setTimeControl(){
        viewState.initTimeControl(eventObj.hours, eventObj.minutes)
        updateDateTimeArea()
    }

    private fun updateDateTimeArea(){
        viewState.showDate(eventObj.hours, eventObj.minutes, "TODAY")
    }
}