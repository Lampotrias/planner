package com.lamp.planner.presentation.features

import android.content.Context
import com.lamp.planner.R
import java.io.Serializable

enum class NotifyTimeInterval(val minutes: Int, val hours: Int, val days: Int) : Serializable {
    NONE(0, 0, 0),
    MINUTES_5(5, 0, 0),
    MINUTES_15(15, 0, 0),
    MINUTES_30(30, 0, 0),
    HOURS_1(0, 1, 0),
    HOURS_3(0, 3, 0),
    DAYS_1(0, 0, 1)
}

object NotifyIntervalTools {
    fun getTextReminder(context: Context, timeInterval: NotifyTimeInterval): String {
        return when (timeInterval) {
            NotifyTimeInterval.NONE -> ""
            NotifyTimeInterval.MINUTES_5 -> context.resources.getString(R.string.notify_interval_5min)
            NotifyTimeInterval.MINUTES_15 -> context.resources.getString(R.string.notify_interval_15min)
            NotifyTimeInterval.MINUTES_30 -> context.resources.getString(R.string.notify_interval_30min)
            NotifyTimeInterval.HOURS_1 -> context.resources.getString(R.string.notify_interval_1hour)
            NotifyTimeInterval.HOURS_3 -> context.resources.getString(R.string.notify_interval_3hour)
            NotifyTimeInterval.DAYS_1 -> context.resources.getString(R.string.notify_interval_1day)
        }
    }
}
