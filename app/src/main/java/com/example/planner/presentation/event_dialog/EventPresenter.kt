package com.example.planner.presentation.event_dialog

import com.example.planner.presentation.base.BasePresenter
import timber.log.Timber
import javax.inject.Inject

class EventPresenter @Inject constructor(): BasePresenter<EventView>() {
    fun Log(s: String){
        viewState.close()
    }
}