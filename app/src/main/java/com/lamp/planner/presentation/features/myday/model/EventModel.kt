package com.lamp.planner.presentation.features.myday.model

import com.lamp.planner.presentation.DisplayableItem
import java.text.DateFormat
import java.util.Calendar

data class EventModel(
    var id: Long,
    var name: String,
    var time: Long,
    var groupName: String,
    var format: String = DateFormat.FULL.toString()
) : DisplayableItem {
    val isExpired: Boolean
        get() = time < Calendar.getInstance().timeInMillis
}
