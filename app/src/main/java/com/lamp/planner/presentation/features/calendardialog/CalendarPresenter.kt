package com.lamp.planner.presentation.features.calendardialog

import com.lamp.planner.domain.Constants
import com.lamp.planner.domain.utils.CalendarUtils
import com.lamp.planner.presentation.base.BasePresenter
import com.lamp.planner.presentation.features.myday.EventTransferObject
import timber.log.Timber
import javax.inject.Inject

class CalendarPresenter @Inject constructor() : BasePresenter<CalendarView>() {
    private lateinit var eventObj: EventTransferObject

    fun clickSubmit() {
        viewState.close(eventObj)
    }

    fun setInputNavArgs(calendarDialogArgs: CalendarDialogArgs) {
        eventObj = calendarDialogArgs.eventObj
        drawTimeControl()
        drawCalendarControl()
        Timber.tag("inp_cal").e(eventObj.toString())
    }

    private fun drawCalendarControl() {
        viewState.initCalendarControl(eventObj.year, eventObj.month, eventObj.day)
    }

    fun setDate(year: Int, month: Int, day: Int) {
        eventObj.year = year
        eventObj.month = month
        eventObj.day = day
        updateDateTimeArea()
    }

    fun setTime(hours: Int, minutes: Int) {
        eventObj.hours = hours
        eventObj.minutes = minutes
        eventObj.allDay = Constants.ALL_DAY_N
        drawTimeControl()
    }

    private fun drawTimeControl() {
        viewState.initTimeControl(eventObj.hours, eventObj.minutes)
        updateDateTimeArea()
    }

    private fun updateDateTimeArea() {
        viewState.showFormattedTime(
            CalendarUtils.getDayInString(
                eventObj.year,
                eventObj.month,
                eventObj.day,
                Constants.FORMAT_TIME
            ),
            eventObj.getStrTime(),
            eventObj.allDay
        )
    }
}
