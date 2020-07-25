package com.lamp.planner.presentation.background.workmanager

//class SendNotifyWorker @AssistedInject constructor(
//    @Assisted private val appContext: Context,
//    @Assisted private val params: WorkerParameters,
//    private val getEventByIdInteractor: GetEventByIdInteractor,
//    private val notificationHelper: NotificationHelper
//) : Worker(appContext, params) {
//
//    override fun doWork(): Result {
//        val eventId = inputData.getLong(Constants.EVENT_ID, 0)
//        if (eventId > 0) {
//            getEventByIdInteractor(GetEventByIdInteractor.Params(eventId)) {
//                it.fold(
//                    this::handleError,
//                    this::successGetById
//                )
//            }
//        }
//        return Result.success()
//    }
//
//    private fun successGetById(event: Event) {
//        notificationHelper.sendShortNotify(
//            event.id.toInt(),
//            event.time,
//            event.name,
//            "Входящие задачи"
//        )
//        Timber.tag("test111work").e(Date().toString())
//        Timber.tag("test111work").e(event.toString())
//    }
//
//    private fun handleError(failure: Failure) {
//        Timber.tag("test111work").e(failure.toString())
//    }
//
//    @AssistedInject.Factory
//    interface Factory : ChildWorkerFactory
//}
