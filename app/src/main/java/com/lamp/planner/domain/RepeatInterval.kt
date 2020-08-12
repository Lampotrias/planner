package com.lamp.planner.domain

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Date

sealed class RepeatTypes : TypesInfo {
    object Always : RepeatTypes() {
        override fun getStatusText(): String = "Повторение без ограничений"
    }

    class UntilCount(val count: Int = 1) : RepeatTypes() {
        override fun getStatusText(): String = "$count повторений"
    }

    class UntilDate(val untilDate: Long) : RepeatTypes() {
        override fun getStatusText(): String {
            val format = SimpleDateFormat.getDateInstance()
            val strDate = format.format(Date(untilDate))
            return "Повторение до $strDate"
        }
    }
}

interface TypesInfo {
    fun getStatusText(): String
}

abstract class RepeatInterval : Serializable {
    abstract var type: RepeatTypes
    abstract var repeatInterval: Int
    abstract fun getNextAlarm(): Long
    abstract fun getPeriodName(): String
}

object None : RepeatInterval() {
    override var type: RepeatTypes = RepeatTypes.Always
    override var repeatInterval = 1
    override fun getNextAlarm(): Long = 0L
    override fun getPeriodName(): String = ""
}

class DayRepeat : RepeatInterval() {
    override var type: RepeatTypes = RepeatTypes.Always
    override var repeatInterval: Int = 1
    override fun getNextAlarm(): Long {
        return 0L
    }

    override fun getPeriodName() = "День"
}

class WeekRepeat : RepeatInterval() {
    override var type: RepeatTypes = RepeatTypes.Always
    override var repeatInterval: Int = 1
    override fun getNextAlarm(): Long {
        return 0L
    }

    override fun getPeriodName() = "Неделя"
}

class MonthRepeat : RepeatInterval() {
    override var type: RepeatTypes = RepeatTypes.Always
    override var repeatInterval: Int = 1
    override fun getNextAlarm(): Long {
        return 0L
    }

    override fun getPeriodName() = "Месяц"
}

class YearRepeat : RepeatInterval() {
    override var type: RepeatTypes = RepeatTypes.Always
    override var repeatInterval: Int = 1
    override fun getNextAlarm(): Long {
        return 0L
    }

    override fun getPeriodName() = "Год"
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
