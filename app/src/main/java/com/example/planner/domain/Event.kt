package com.example.planner.domain

import com.example.planner.data.database.EventEntity
import java.time.ZoneOffset

data class Event(
    var id: Long,
    var name: String,
    var time: Long,
    var zoneOffset: Int
) {

    val localTime: Long
        get() = time + zoneOffset

    fun toEventEntity(): EventEntity {
        return EventEntity(id, name, time, zoneOffset)
    }
}