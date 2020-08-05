package com.lamp.planner.domain

import android.view.ViewGroup
import java.io.Serializable

sealed class RepeatInterval : Serializable

object None : RepeatInterval()
class DayRepeat(var string: String) : RepeatInterface, RepeatInterval() {
    var params: RepeatParams = CountRepeat(1)
    override fun getNextAlarm(): Long {
        return 0L
    }

    override fun getPeriodName() = "День"
    override fun getLayoutParam(): ViewGroup {
        TODO("Not yet implemented")
    }
}

class WeekRepeat(var string: String) : RepeatInterface, RepeatInterval() {
    override fun getNextAlarm(): Long {
        return 0L
    }

    override fun getPeriodName() = "Неделя"
    override fun getLayoutParam(): ViewGroup {
        TODO("Not yet implemented")
    }
}

class MonthRepeat(var string: String) : RepeatInterface, RepeatInterval() {
    override fun getNextAlarm(): Long {
        return 0L
    }

    override fun getPeriodName() = "Месяц"
    override fun getLayoutParam(): ViewGroup {
        TODO("Not yet implemented")
    }
}

class YearRepeat(var string: String) : RepeatInterface, RepeatInterval() {
    override fun getNextAlarm(): Long {
        return 0L
    }

    override fun getPeriodName() = "Год"
    override fun getLayoutParam(): ViewGroup {
        TODO("Not yet implemented")
    }
}

sealed class RepeatParams
class Always(val value: Int) : RepeatParams()
class CountRepeat(val value: Int) : RepeatParams()
class UntilDate(val value: Int) : RepeatParams()

fun test1(test: RepeatParams) = when (test) {
    is CountRepeat -> ""
    is UntilDate -> ""
    is Always -> ""
}

interface RepeatInterface : Serializable {
    fun getNextAlarm(): Long
    fun getPeriodName(): String
    fun getLayoutParam(): ViewGroup
}

enum class CustomDayRepeatEnum {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}
