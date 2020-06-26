package com.example.planner.domain.utils

import com.example.planner.domain.Constants
import java.text.SimpleDateFormat
import java.util.*

object CalendarUtils {

    private fun isYesterdayTimestamp(timestamp: Long): Boolean {
        val start = Calendar.getInstance()
        start.add(Calendar.DAY_OF_MONTH, -1)
        start.set(Calendar.HOUR_OF_DAY, 0)
        start.set(Calendar.MINUTE, 0)
        start.set(Calendar.SECOND, 0)

        val end = Calendar.getInstance()
        end.add(Calendar.DAY_OF_MONTH, -1)
        end.set(Calendar.HOUR_OF_DAY, 23)
        end.set(Calendar.MINUTE, 59)
        end.set(Calendar.SECOND, 59)
        return (timestamp >= start.timeInMillis && timestamp <= end.timeInMillis)
    }

    fun isLastDayTimestamp(timestamp: Long): Boolean {
        val yesterday = Calendar.getInstance()
        yesterday.add(Calendar.DAY_OF_MONTH, -1)
        yesterday.set(Calendar.HOUR_OF_DAY, 23)
        yesterday.set(Calendar.MINUTE, 59)
        yesterday.set(Calendar.SECOND, 59)
        return timestamp <= yesterday.timeInMillis
    }

    fun isTodayTimestamp(timestamp: Long): Boolean {
        val todayStart = Calendar.getInstance()
        todayStart.set(Calendar.HOUR_OF_DAY, 0)
        todayStart.set(Calendar.MINUTE, 0)
        todayStart.set(Calendar.SECOND, 0)

        val todayEnd = Calendar.getInstance()
        todayEnd.set(Calendar.HOUR_OF_DAY, 23)
        todayEnd.set(Calendar.MINUTE, 59)
        todayEnd.set(Calendar.SECOND, 59)
        return timestamp > todayStart.timeInMillis && timestamp <= todayEnd.timeInMillis
    }

    fun isTomorrowTimestamp(timestamp: Long): Boolean {
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

    fun isLaterTimestamp(timestamp: Long): Boolean {
        val endWeek = Calendar.getInstance()
        endWeek.set(Calendar.WEEK_OF_YEAR, endWeek.get(Calendar.WEEK_OF_YEAR) + 1)
        endWeek.set(Calendar.HOUR_OF_DAY, 0)
        endWeek.set(Calendar.MINUTE, 0)
        endWeek.set(Calendar.SECOND, 0)
        endWeek.add(Calendar.SECOND, -1)
        return timestamp < endWeek.timeInMillis
    }

    fun isNextWeekTimestamp(timestamp: Long): Boolean {
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

    fun isFutureTimestamp(timestamp: Long): Boolean {
        val endSecondWeek = Calendar.getInstance()
        endSecondWeek.set(Calendar.WEEK_OF_YEAR, endSecondWeek.get(Calendar.WEEK_OF_YEAR) + 2)
        endSecondWeek.set(Calendar.HOUR_OF_DAY, 0)
        endSecondWeek.set(Calendar.MINUTE, 0)
        endSecondWeek.set(Calendar.SECOND, 0)

        return timestamp > endSecondWeek.timeInMillis
    }

    fun getDayInString(timestamp: Long, format: String): String {
        return when {
            isYesterdayTimestamp(timestamp) -> Constants.YESTERDAY
            isTodayTimestamp(timestamp) -> Constants.TODAY
            isTomorrowTimestamp(timestamp) -> Constants.TOMORROW
            else -> formatDate(timestamp, format)
        }
    }

    fun getDayInString(year: Int, month: Int, day: Int, format: String): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return getDayInString(calendar.timeInMillis, format)
    }

    private fun formatDate(timestamp: Long, format: String): String {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        val date = Date(timestamp)
        return sdf.format(date)
    }

    fun formatTime(hours: Int, minutes: Int): String {
        val h = if (hours.toString().length == 1) "0$hours" else "$hours"
        val m = if (minutes.toString().length == 1) "0$minutes" else "$minutes"
        return "$h:$m"
    }
}
