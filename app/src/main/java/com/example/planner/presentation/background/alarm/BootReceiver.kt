package com.example.planner.presentation.background.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {
    /***
     *
     * Notice that in the manifest, the boot receiver is set to android:enabled="false". This means that the receiver will not be called unless the application explicitly enables it. This prevents the boot receiver from being called unnecessarily. You can enable a receiver (for example, if the user sets an alarm) as follows:

    KOTLIN
    val receiver = ComponentName(context, SampleBootReceiver::class.java)

    context.packageManager.setComponentEnabledSetting(
    receiver,
    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
    PackageManager.DONT_KILL_APP
    )


     cancel

    val receiver = ComponentName(context, SampleBootReceiver::class.java)

    context.packageManager.setComponentEnabledSetting(
    receiver,
    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
    PackageManager.DONT_KILL_APP
    )
     */
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            // Set the alarm here.
        }
    }
}