package com.lamp.planner.presentation.background.workmanager

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.lamp.planner.domain.Constants
import com.lamp.planner.domain.Event
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.domain.interactors.GetEventByIdInteractor
import com.lamp.planner.presentation.background.NotificationHelper
import timber.log.Timber
import java.util.Date

class SendNotifyWorker @WorkerInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val getEventByIdInteractor: GetEventByIdInteractor,
    private val notificationHelper: NotificationHelper
) : Worker(context, workerParameters) {

    override fun doWork(): Result {
        Timber.tag("test22").e(Date().toString())
        val eventId = inputData.getLong(Constants.EVENT_ID, 0)
        if (eventId > 0) {
            getEventByIdInteractor(GetEventByIdInteractor.Params(eventId)) {
                it.fold(
                    ::handleError,
                    ::successGetById
                )
            }
        }
        return Result.success()
    }

    private fun successGetById(event: Event) {
        Timber.tag("test333work").e(Date().toString())
        notificationHelper.sendShortNotify(
            event.id.toInt(),
            event.time,
            event.name,
            "Входящие задачи"
        )
        Timber.tag("test111work").e(Date().toString())
        Timber.tag("test111work").e(event.toString())
    }

    private fun handleError(failure: Failure) {
        Timber.tag("test111work").e(failure.toString())
    }

//    @AssistedInject.Factory
//    interface Factory : ChildWorkerFactory
}
