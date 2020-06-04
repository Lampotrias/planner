package com.example.planner.domain.utils

import java.util.*

object CalendarUtils {
    fun forLastDayTimestamp(timestamp: Long): Boolean{
        val yesterday = Calendar.getInstance()
        yesterday.add(Calendar.DAY_OF_MONTH, -1)
        yesterday.set(Calendar.HOUR, 11)
        yesterday.set(Calendar.MINUTE, 59)
        yesterday.set(Calendar.SECOND, 59)
        return timestamp <= yesterday.timeInMillis
    }

    fun forTodayTimestamp(timestamp: Long): Boolean{
        val today = Calendar.getInstance()
        today.set(Calendar.HOUR, 11)
        today.set(Calendar.MINUTE, 59)
        today.set(Calendar.SECOND, 59)
        return timestamp <= today.timeInMillis
    }

    fun forTomorrowTimestamp(timestamp: Long): Boolean{
        val start = Calendar.getInstance()
        start.add(Calendar.DAY_OF_MONTH, 1)
        start.set(Calendar.HOUR, 0)
        start.set(Calendar.MINUTE, 0)
        start.set(Calendar.SECOND, 0)

        val end = Calendar.getInstance()
        end.add(Calendar.DAY_OF_MONTH, 1)
        end.set(Calendar.HOUR, 11)
        end.set(Calendar.MINUTE, 59)
        end.set(Calendar.SECOND, 59)

        return (timestamp >= start.timeInMillis && timestamp <= end.timeInMillis)
    }

    fun forLaterTimestamp(timestamp: Long): Boolean{
        val tomorrow = Calendar.getInstance()
        tomorrow.add(Calendar.DAY_OF_MONTH, 2)
        tomorrow.set(Calendar.HOUR, 0)
        tomorrow.set(Calendar.MINUTE, 0)
        tomorrow.set(Calendar.SECOND, 0)
        return timestamp > tomorrow.timeInMillis
    }
}