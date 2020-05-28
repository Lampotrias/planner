package com.example.planner.presentation.background

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber

class NotifyBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.tag("test111").e(intent?.extras.toString())
        Timber.tag("test1111").e(intent?.getStringExtra("test"))
        Timber.tag("test11111").e(intent?.toString())
    }
}