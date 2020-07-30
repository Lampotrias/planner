package com.lamp.planner.domain

import com.lamp.planner.data.database.EventEntity

data class Event(
    var id: Long,
    var name: String,
    var time: Long,
    var allDay: Int,
    var zoneOffset: Int,
    var groupId: Long,
    var notifyTime: Long,
    var image: Int?
) {
    fun toEventEntity() =
        EventEntity(id, name, time, allDay, zoneOffset, groupId, notifyTime, image)
}
