package com.example.planner.presentation.background.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.planner.domain.Constants
import com.example.planner.presentation.background.workmanager.SendNotifyWorker
import timber.log.Timber
import java.util.*

class BaseAlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {

        val eventId = intent?.run {
            getLongExtra(Constants.EVENT_ID, 0)
        }
        val data = workDataOf(Constants.EVENT_ID to eventId)

        val worker = OneTimeWorkRequestBuilder<SendNotifyWorker>()
            .setInputData(data)
            .build()
        WorkManager.getInstance(context).enqueue(worker)

        Timber.tag("test111").e(Date().toString())
    }
}