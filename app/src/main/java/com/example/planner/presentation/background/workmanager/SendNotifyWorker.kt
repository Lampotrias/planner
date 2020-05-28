package com.example.planner.presentation.background.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.planner.domain.Constants
import com.example.planner.domain.Event
import com.example.planner.domain.excetion.Failure
import com.example.planner.domain.interactors.GetEventByIdInteractor
import com.example.planner.presentation.NotificationHelper
import com.example.planner.presentation.background.alarm.AlarmTaskManager
import com.example.planner.presentation.background.workmanager.ChildWorkerFactory
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import timber.log.Timber
import java.util.*

class SendNotifyWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val params: WorkerParameters,
    private val getEventByIdInteractor: GetEventByIdInteractor,
    private val notificationHelper: NotificationHelper
) : Worker(appContext, params) {

    override fun doWork(): Result {
        val eventId = inputData.getLong(Constants.EVENT_ID, 0)
        if (eventId > 0) {
            getEventByIdInteractor(GetEventByIdInteractor.Params(eventId)){it.fold(this::handleError, this::successGetById)}
        }
        return Result.success()
    }

    private fun successGetById(event: Event){
        notificationHelper.sendShortNotify(event.id.toInt(), event.name, "Входящие задачи")
        Timber.tag("test111work").e(Date().toString())
        Timber.tag("test111work").e(event.toString())
    }

    private fun handleError(failure: Failure){
        Timber.tag("test111work").e("error")
    }

    @AssistedInject.Factory
    interface Factory : ChildWorkerFactory
}