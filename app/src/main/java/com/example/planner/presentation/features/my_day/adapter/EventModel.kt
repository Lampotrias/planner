package com.example.planner.presentation.features.my_day.adapter

import com.example.planner.presentation.DisplayableItem
import java.text.DateFormat
import java.util.*

data class EventModel(
    var id: Long,
    var name: String,
    var time: Long,
    var format: String = DateFormat.FULL.toString()
) : DisplayableItem {
    val isExpired: Boolean
        get() = time < Calendar.getInstance().timeInMillis
}