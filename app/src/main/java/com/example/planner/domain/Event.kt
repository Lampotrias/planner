package com.example.planner.domain

import com.example.planner.data.database.EventEntity

data class Event(
    var id: Long,
    var name: String,
    var time: Long,
    val allDay: Int,
    var zoneOffset: Int,
    val groupId: Long
) {

    val localTime: Long
        get() = time + zoneOffset

    fun toEventEntity() = EventEntity(id, name, time, allDay, zoneOffset, groupId)
}
