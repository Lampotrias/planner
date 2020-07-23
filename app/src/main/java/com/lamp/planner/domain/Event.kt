package com.lamp.planner.domain

import com.lamp.planner.data.database.EventEntity

data class Event(
    var id: Long,
    var name: String,
    var time: Long,
    val allDay: Int,
    var zoneOffset: Int,
    val groupId: Long
) {
    fun toEventEntity() = EventEntity(id, name, time, allDay, zoneOffset, groupId)
}
