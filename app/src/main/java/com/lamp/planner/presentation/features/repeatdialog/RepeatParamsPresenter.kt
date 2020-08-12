package com.lamp.planner.presentation.features.repeatdialog

import com.lamp.planner.domain.RepeatInterval
import com.lamp.planner.domain.RepeatTypes
import com.lamp.planner.presentation.base.BasePresenter
import java.util.Calendar
import javax.inject.Inject

class RepeatParamsPresenter @Inject constructor() : BasePresenter<RepeatParamView>(), StateOwner {
    private var state: State = InitState()
    private lateinit var obj: RepeatInterval

    init {
        state.setOwner(this)
    }

    override fun attachView(view: RepeatParamView?) {
        super.attachView(view)
        state.viewAttached()
    }

    fun setInputNavArgs(args: RepeatParamsDialogArgs) {
        obj = args.repeatInterval

        viewState.setRepeatInterval(obj.repeatInterval)

        when (val type = obj.type) {
            is RepeatTypes.Always -> setState(AlwaysState())
            is RepeatTypes.UntilDate -> setState(UntilDateState(type.untilDate))
            is RepeatTypes.UntilCount -> setState(UntilCountState(type.count))
        }
    }

    override fun setState(state: State) {
        this.state.onExit()
        this.state = state.also {
            it.setOwner(this)
            it.onEnter(viewState)
        }
    }

    override fun getView(): RepeatParamView = viewState
    override fun getPeriodName() = obj.getPeriodName()
    override fun getStatusText() = obj.type.getStatusText()

    override fun getRepeatInterval() = obj.repeatInterval
    override fun getRepeatCount(): Int {
        return when (obj.type) {
            is RepeatTypes.UntilCount -> (obj.type as RepeatTypes.UntilCount).count
            else -> 1
        }
    }

    override fun setAlwaysType() {
        obj.type = RepeatTypes.Always
    }

    override fun setUntilDateType(timestamp: Long) {
        obj.type = RepeatTypes.UntilDate(timestamp)
    }

    override fun setUntilCountType(count: Int) {
        obj.type = RepeatTypes.UntilCount(count)
    }

    fun clickOkCalendar(year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        setState(UntilDateState(calendar.timeInMillis))
    }

    fun clickAlways() {
        setState(AlwaysState())
    }

    fun clickUntilDate() {
        setState(OpenCalendarState())
    }

    fun submitChildDialog(progress: Int) {
        setState(UntilCountState(progress))
    }

    fun clickUntilCounter() {
        setState(OpenChildDialogState())
    }

    fun clickSubmit(repeatInterval: Int) {
        obj.repeatInterval = repeatInterval
        viewState.close(obj)
    }
}

abstract class State {
    protected lateinit var stateOwner: StateOwner
    open fun onEnter(view: RepeatParamView) {
        invalidateView(view)
    }

    open fun invalidateView(view: RepeatParamView) {}
    open fun onExit() {}
    open fun viewAttached() {
        invalidateView(stateOwner.getView())
    }

    fun setOwner(owner: StateOwner) {
        stateOwner = owner
    }
}

class InitState : State() {
    override fun viewAttached() {
        super.viewAttached()

        stateOwner.setState(AlwaysState())
    }
}

class AlwaysState : State() {
    override fun onEnter(view: RepeatParamView) {
        super.onEnter(view)
        stateOwner.setAlwaysType()
        view.setAlways(
            stateOwner.getStatusText(),
            stateOwner.getPeriodName()
        )
    }
}

class OpenCalendarState : State() {
    override fun onEnter(view: RepeatParamView) {
        super.onEnter(view)
        view.openCalendar()
    }
}

class UntilDateState(private val timestamp: Long) : State() {
    override fun onEnter(view: RepeatParamView) {
        super.onEnter(view)
        stateOwner.setUntilDateType(timestamp)
        view.setUntilDate(
            stateOwner.getStatusText(),
            stateOwner.getPeriodName()
        )
    }
}

class OpenChildDialogState : State() {
    override fun onEnter(view: RepeatParamView) {
        super.onEnter(view)
        view.showChildParamsDialog(true, stateOwner.getRepeatCount())
    }

    override fun onExit() {
        super.onExit()
        stateOwner.getView().showChildParamsDialog(false)
    }
}

class UntilCountState(private val count: Int) : State() {
    override fun onEnter(view: RepeatParamView) {
        super.onEnter(view)
        stateOwner.setUntilCountType(count)
        view.setCount(
            stateOwner.getStatusText(),
            stateOwner.getPeriodName()
        )
    }
}

interface StateOwner {
    fun setState(state: State)
    fun getView(): RepeatParamView
    fun getPeriodName(): String
    fun getStatusText(): String
    fun getRepeatInterval(): Int
    fun getRepeatCount(): Int
    fun setAlwaysType()
    fun setUntilDateType(timestamp: Long)
    fun setUntilCountType(count: Int)
}
