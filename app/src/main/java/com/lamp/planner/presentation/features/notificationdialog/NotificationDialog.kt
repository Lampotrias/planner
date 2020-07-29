package com.lamp.planner.presentation.features.notificationdialog

import android.os.Bundle
import android.util.SizeF
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import by.kirich1409.viewbindingdelegate.viewBinding
import com.lamp.planner.R
import com.lamp.planner.databinding.DialogNotifySetBinding
import com.lamp.planner.presentation.base.BaseDialog
import com.lamp.planner.presentation.features.NotifyTimeInterval

class NotificationDialog : BaseDialog() {

    private val binding by viewBinding {
        DialogNotifySetBinding.bind(it.requireView())
    }

    companion object {
        const val NOTIFY_SET_DIALOG_REQUEST_KEY = "NOTIFY_SET_DIALOG_REQUEST_KEY"
        const val NOTIFY_SET_DIALOG_RESULT_INTERVAL = "NOTIFY_SET_DIALOG_RESULT_TIMESTAMP"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_notify_set, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            notifyIntervalEvent5min.setOnClickListener { notifyClick(NotifyTimeInterval.MINUTES_5) }
            notifyIntervalEvent15min.setOnClickListener { notifyClick(NotifyTimeInterval.MINUTES_15) }
            notifyIntervalEvent30min.setOnClickListener { notifyClick(NotifyTimeInterval.MINUTES_30) }
            notifyIntervalEvent1hour.setOnClickListener { notifyClick(NotifyTimeInterval.HOURS_1) }
            notifyIntervalEvent3hour.setOnClickListener { notifyClick(NotifyTimeInterval.HOURS_3) }
            notifyIntervalEvent1day.setOnClickListener { notifyClick(NotifyTimeInterval.DAYS_1) }
        }
    }

    override fun setSizeDialog() = SizeF(0.9f, 0f)

    private fun notifyClick(timeInterval: NotifyTimeInterval) {
        close(timeInterval)
    }

    private fun close(timeInterval: NotifyTimeInterval) {
        setFragmentResult(
            NOTIFY_SET_DIALOG_REQUEST_KEY,
            bundleOf(NOTIFY_SET_DIALOG_RESULT_INTERVAL to timeInterval)
        )
        dialog?.dismiss()
    }
}
