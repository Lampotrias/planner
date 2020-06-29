package com.lamp.planner.presentation.background.alarm

import android.app.AlarmManager
import android.app.AlarmManager.RTC_WAKEUP
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.lamp.planner.domain.Constants
import timber.log.Timber
import java.util.*

class AlarmTaskManager(private val context: Context, private val alarmManager: AlarmManager) {

    fun createNotify(eventId: Long, timestamp: Long) {
        Timber.tag("test111AlarmTaskManager").e(Date(timestamp).toString())
        schedulePendingIntent(timestamp, getPendingIntent(eventId))
    }

    private fun getPendingIntent(eventId: Long): PendingIntent {
        val notificationIntent = Intent(context, BaseAlarmReceiver::class.java).apply {
            putExtra(Constants.EVENT_ID, eventId)
        }

        return PendingIntent.getBroadcast(
            context,
            eventId.toInt(),
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun schedulePendingIntent(triggerTimeMillis: Long, pendingIntent: PendingIntent) {

        if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setExactAndAllowWhileIdle(RTC_WAKEUP, triggerTimeMillis, pendingIntent)
        } else {
            if (Build.VERSION.SDK_INT >= 19) {
                alarmManager.setExact(RTC_WAKEUP, triggerTimeMillis, pendingIntent)
            } else {
                alarmManager[RTC_WAKEUP, triggerTimeMillis] = pendingIntent
            }
        }
    }

    fun stop() {
        val intent = Intent(context, BaseAlarmReceiver::class.java)
        val sender = PendingIntent.getBroadcast(context, 11, intent, 0)
        alarmManager.cancel(sender)
    }
}
