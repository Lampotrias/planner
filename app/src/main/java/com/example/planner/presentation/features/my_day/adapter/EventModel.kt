package com.example.planner.presentation.features.my_day.adapter

import java.util.*

data class EventModel(
    var id: Long,
    var name: String,
    var time: String
) {
    val isExpired: Boolean = false
}