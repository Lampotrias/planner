package com.example.planner.domain.utils

import java.util.*

object CalendarUtils {
    fun forLastDayTimestamp(timestamp: Long): Boolean {
        val yesterday = Calendar.getInstance()
        yesterday.add(Calendar.DAY_OF_MONTH, -1)
        yesterday.set(Calendar.HOUR_OF_DAY, 23)
        yesterday.set(Calendar.MINUTE, 59)
        yesterday.set(Calendar.SECOND, 59)
        return timestamp <= yesterday.timeInMillis
    }

    fun forTodayTimestamp(timestamp: Long): Boolean {
        val today = Calendar.getInstance()
        today.set(Calendar.HOUR_OF_DAY, 23)
        today.set(Calendar.MINUTE, 59)
        today.set(Calendar.SECOND, 59)
        return timestamp <= today.timeInMillis
    }

    fun forTomorrowTimestamp(timestamp: Long): Boolean {
        val start = Calendar.getInstance()
        start.add(Calendar.DAY_OF_MONTH, 1)
        start.set(Calendar.HOUR_OF_DAY, 0)
        start.set(Calendar.MINUTE, 0)
        start.set(Calendar.SECOND, 0)

        val end = Calendar.getInstance()
        end.add(Calendar.DAY_OF_MONTH, 1)
        end.set(Calendar.HOUR_OF_DAY, 23)
        end.set(Calendar.MINUTE, 59)
        end.set(Calendar.SECOND, 59)
        return (timestamp >= start.timeInMillis && timestamp <= end.timeInMillis)
    }

    fun forLaterTimestamp(timestamp: Long): Boolean {
        val endWeek = Calendar.getInstance()
        endWeek.set(Calendar.WEEK_OF_YEAR, endWeek.get(Calendar.WEEK_OF_YEAR) + 1)
        endWeek.set(Calendar.HOUR_OF_DAY, 0)
        endWeek.set(Calendar.MINUTE, 0)
        endWeek.set(Calendar.SECOND, 0)
        endWeek.add(Calendar.SECOND, -1)
        return timestamp < endWeek.timeInMillis
    }

    fun forNextWeek(timestamp: Long): Boolean {
        val startWeek = Calendar.getInstance()
        startWeek.set(Calendar.WEEK_OF_YEAR, startWeek.get(Calendar.WEEK_OF_YEAR) + 1)
        startWeek.set(Calendar.HOUR_OF_DAY, 0)
        startWeek.set(Calendar.MINUTE, 0)
        startWeek.set(Calendar.SECOND, 0)

        val endWeek = Calendar.getInstance()
        endWeek.set(Calendar.WEEK_OF_YEAR, endWeek.get(Calendar.WEEK_OF_YEAR) + 2)
        endWeek.set(Calendar.HOUR_OF_DAY, 0)
        endWeek.set(Calendar.MINUTE, 0)
        endWeek.set(Calendar.SECOND, 0)
        endWeek.add(Calendar.SECOND, -1)

        return timestamp < endWeek.timeInMillis && timestamp > startWeek.timeInMillis
    }

    fun forFuture(timestamp: Long): Boolean {
        val endSecondWeek = Calendar.getInstance()
        endSecondWeek.set(Calendar.WEEK_OF_YEAR, endSecondWeek.get(Calendar.WEEK_OF_YEAR) + 2)
        endSecondWeek.set(Calendar.HOUR_OF_DAY, 0)
        endSecondWeek.set(Calendar.MINUTE, 0)
        endSecondWeek.set(Calendar.SECOND, 0)

        return timestamp > endSecondWeek.timeInMillis
    }
}
