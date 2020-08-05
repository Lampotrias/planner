package com.lamp.planner.presentation.features.calendardialog

import com.lamp.planner.domain.Constants
import com.lamp.planner.domain.DayRepeat
import com.lamp.planner.domain.EventTransferObject
import com.lamp.planner.domain.MonthRepeat
import com.lamp.planner.domain.None
import com.lamp.planner.domain.RepeatInterval
import com.lamp.planner.domain.WeekRepeat
import com.lamp.planner.domain.YearRepeat
import com.lamp.planner.domain.utils.CalendarUtils
import com.lamp.planner.presentation.base.BasePresenter
import com.lamp.planner.presentation.features.NotifyTimeInterval
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

    fun clickReminder() {
        val navDirections = CalendarDialogDirections.actionCalendarDialogToNotificationDialog()
        viewState.navigateReminderDialog(navDirections)
    }

    fun setReminder(timeInterval: NotifyTimeInterval) {
        eventObj.reminderInterval = timeInterval.also { viewState.updateReminderStatus(it) }
    }

    fun setRepeat(repeat: RepeatInterval) {
        when (repeat) {
            is None -> Timber.tag("repeatType").e("None")
            is DayRepeat -> Timber.tag("repeatType").e("DayRepeat")
            is WeekRepeat -> Timber.tag("repeatType").e("WeekRepeat")
            is MonthRepeat -> Timber.tag("repeatType").e("MonthRepeat")
            is YearRepeat -> Timber.tag("repeatType").e("YearRepeat")
        }
    }

    fun clickRepeat() {
        val navDirections = CalendarDialogDirections.actionCalendarDialogToRepeatDialog()
        viewState.navigateRepeatDialog(navDirections)
    }
}
