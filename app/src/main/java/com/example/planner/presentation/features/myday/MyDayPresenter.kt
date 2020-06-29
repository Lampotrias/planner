package com.example.planner.presentation.features.myday

import com.example.planner.domain.Event
import com.example.planner.domain.Subscriber
import com.example.planner.domain.excetion.Failure
import com.example.planner.domain.interactors.GetAllEventsInteractor
import com.example.planner.domain.interactors.GlobalInteractor
import com.example.planner.domain.interactors.None
import com.example.planner.domain.interactors.SaveAndReturnEventInteractor
import com.example.planner.domain.utils.CalendarUtils
import com.example.planner.presentation.DisplayableItem
import com.example.planner.presentation.adapters.ManagerImpl
import com.example.planner.presentation.background.NotificationHelper
import com.example.planner.presentation.background.alarm.AlarmTaskManager
import com.example.planner.presentation.base.BasePresenter
import com.example.planner.presentation.features.myday.adapter.EventItemDelegateAdapter
import com.example.planner.presentation.features.myday.adapter.EventModel
import com.example.planner.presentation.features.myday.adapter.TimeEventDelegateAdapter
import com.example.planner.presentation.features.myday.adapter.TimeEventModel
import timber.log.Timber
import java.util.*
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

    override fun provideState(state: Int) {
        Timber.e("state= %s", state.toString())
    }

    override fun provideOffset(offset: Float) {
        viewState.showAnimationFab(offset)
        Timber.e("Offset= %s", offset.toString())
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
        val calendar = Calendar.getInstance()
        calendar.set(event.year, event.month, event.day, event.hours, event.minutes, 0)
        saveAndReturnEventInteractor(
            Event(
                0,
                event.name,
                calendar.timeInMillis,
                event.allDay,
                TimeZone.getDefault().rawOffset,
                event.groupId
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
        alarmManager.createNotify(event.id, event.time)
        viewState.showSuccessEventAddMessage()
        getAllEvents()
    }

    private fun successGetEventList(list: List<Event>) {
        val eventsModel: MutableList<EventModel> = mutableListOf()
        // val format = SimpleDateFormat("d MMMM, HH:mm", Locale.getDefault())

        list.map {
//            val date: Date = Date(it.time)
            eventsModel.add(
                EventModel(
                    it.id,
                    it.name,
                    it.time,
                    "Входящие задачи"
                )
            )
        }

        val manager = ManagerImpl<DisplayableItem>()
        manager.addDelegate(EventItemDelegateAdapter())
        manager.addDelegate(TimeEventDelegateAdapter())

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
            result.add(event)
        }
        return result
    }
}
