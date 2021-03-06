package com.lamp.planner.presentation.features.myday

import com.lamp.planner.domain.Event
import com.lamp.planner.domain.EventTransferObject
import com.lamp.planner.domain.Subscriber
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.domain.interactors.GetAllEventsInteractor
import com.lamp.planner.domain.interactors.GlobalInteractor
import com.lamp.planner.domain.interactors.None
import com.lamp.planner.domain.interactors.SaveAndReturnEventInteractor
import com.lamp.planner.domain.utils.CalendarUtils
import com.lamp.planner.presentation.DisplayableItem
import com.lamp.planner.presentation.adapters.EventItemDelegateAdapter
import com.lamp.planner.presentation.adapters.ManagerImpl
import com.lamp.planner.presentation.adapters.TimeEventDelegateAdapter
import com.lamp.planner.presentation.adapters.TimeEventNowDelegateAdapter
import com.lamp.planner.presentation.background.NotificationHelper
import com.lamp.planner.presentation.background.alarm.AlarmTaskManager
import com.lamp.planner.presentation.base.BasePresenter
import com.lamp.planner.presentation.features.myday.model.EventModel
import com.lamp.planner.presentation.features.myday.model.TimeEventModel
import com.lamp.planner.presentation.features.myday.model.TimeNowModel
import timber.log.Timber
import java.util.Calendar
import java.util.TimeZone
import javax.inject.Inject

class MyDayPresenter @Inject constructor(
    private val globalInteractor: GlobalInteractor,
    private val saveAndReturnEventInteractor: SaveAndReturnEventInteractor,
    private val getAllEventsInteractor: GetAllEventsInteractor
) : BasePresenter<MyDayView>(), Subscriber {

    @Inject
    lateinit var alarmManager: AlarmTaskManager

    @Inject
    lateinit var notificationHelper: NotificationHelper

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getAllEvents()
        globalInteractor.attach(this)
    }

    fun btnStartClick() {
        notificationHelper.sendShortNotify(1, 0L, "title", "body")
    }

    fun btnStopClick() {
        notificationHelper.cancelNotify(1)
    }

    override fun provideState(state: Int) {}

    override fun provideOffset(offset: Float) {
        viewState.showAnimationFab(offset)
    }

    override fun onDestroy() {
        super.onDestroy()
        globalInteractor.deAttach()
    }

    fun showPopupDialog() {
        val directions =
            MyDayFragmentDirections.actionMyDayToEventDialog(
                null
            )
        viewState.showAddEventPopupDialog(directions)
    }

    fun processSaveEvent(event: EventTransferObject) {
        Timber.tag("processSaveEvent").e(event.toString())
        saveAndReturnEventInteractor(
            Event(
                0,
                event.name,
                event.time,
                event.allDay,
                TimeZone.getDefault().rawOffset,
                event.groupId,
                event.notifyTime,
                if (event.image != -1) event.image else null
            )
        ) { it.fold(this::handleError, this::successSaveEvent) }
    }

    private fun getAllEvents() {
        getAllEventsInteractor(None()) { it.fold(this::handleError, this::successGetEventList) }
    }

    private fun handleError(failure: Failure?) {
        viewState.handleFailure(failure)
    }

    private fun successSaveEvent(event: Event) {
        Timber.tag("createNotify").e(event.toString())
        alarmManager.createNotify(event.id, event.notifyTime)
        viewState.showSuccessEventAddMessage()
        getAllEvents()
    }

    private fun successGetEventList(list: List<Event>) {
        val eventsModel: MutableList<EventModel> = mutableListOf()

        list.map {
            eventsModel.add(
                EventModel(
                    id = it.id,
                    name = it.name,
                    time = it.time,
                    groupName = "Входящие задачи",
                    image = it.image
                )
            )
        }

        val manager = ManagerImpl<DisplayableItem>()
        manager.addDelegate(EventItemDelegateAdapter())
        manager.addDelegate(TimeEventDelegateAdapter())
        manager.addDelegate(TimeEventNowDelegateAdapter())

        eventsModel.sortBy { it.time }
        viewState.showEventList(manager, addTimeSeparatorToEventList(eventsModel))
    }

    private fun addTimeSeparatorToEventList(events: List<EventModel>): List<DisplayableItem> {
        val result = mutableListOf<DisplayableItem>()

        var bYesterday = false // просроченные
        var bToday = false // сегодня
        var bTomorrow = false // завтра
        var bLater = false // предстоящие
        var bNextWeek = false // следующая неделя
        var bFuture = false // на будущее
        var bNowPoint = false

        for (event in events) {
            if (CalendarUtils.isLastDayTimestamp(event.time)) {
                if (!bYesterday) {
                    result.add(TimeEventModel("Просроченные"))
                    bYesterday = true
                }
            } else if (CalendarUtils.isTodayTimestamp(event.time)) {
                if (!bToday) {
                    result.add(TimeEventModel("Сегодня"))
                    bToday = true
                }
            } else if (CalendarUtils.isTomorrowTimestamp(event.time)) {
                if (!bTomorrow) {
                    result.add(TimeEventModel("Завтра"))
                    bTomorrow = true
                }
            } else if (CalendarUtils.isLaterTimestamp(event.time)) {
                if (!bLater) {
                    bLater = true
                    result.add(TimeEventModel("Предстоящие"))
                }
            } else if (CalendarUtils.isNextWeekTimestamp(event.time)) {
                if (!bNextWeek) {
                    bNextWeek = true
                    result.add(TimeEventModel("Следующая неделя"))
                }
            } else if (CalendarUtils.isFutureTimestamp(event.time)) {
                if (!bFuture) {
                    bFuture = true
                    result.add(TimeEventModel("на будущее"))
                }
            }
            if (bToday && !bNowPoint) {
                val now = Calendar.getInstance()
                if (event.time > now.timeInMillis) {
                    val strTime = "${now.get(Calendar.HOUR_OF_DAY)}:${now.get(Calendar.MINUTE)}"
                    result.add(TimeNowModel(strTime))
                    bNowPoint = true
                }
            }
            result.add(event)
        }
        return result
    }
}
