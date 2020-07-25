package com.lamp.planner.presentation.background.alarm

//import com.lamp.planner.presentation.background.workmanager.SendNotifyWorker
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.workDataOf
import com.lamp.planner.domain.Constants
import timber.log.Timber
import java.util.*

class BaseAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {

        val eventId = intent?.run {
            getLongExtra(Constants.EVENT_ID, 0)
        }
        val data = workDataOf(Constants.EVENT_ID to eventId)

//        val worker = OneTimeWorkRequestBuilder<SendNotifyWorker>()
//            .setInputData(data)
//            .build()
//        WorkManager.getInstance(context).enqueue(worker)

        Timber.tag("test111").e(Date().toString())
    }
}
