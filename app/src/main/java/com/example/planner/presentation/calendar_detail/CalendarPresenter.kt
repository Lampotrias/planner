package com.example.planner.presentation.calendar_detail

import com.example.planner.presentation.base.BasePresenter
import timber.log.Timber
import javax.inject.Inject

class CalendarPresenter @Inject constructor(): BasePresenter<CalendarView>() {
    fun Log(s: String){
        viewState.close()
    }
}