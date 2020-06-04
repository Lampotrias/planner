package com.example.planner.domain

import com.example.planner.data.database.EventEntity
import com.example.planner.presentation.DisplayableItem
import java.time.ZoneOffset

data class Event(
    var id: Long,
    var name: String,
    var time: Long,
    var zoneOffset: Int,
    val groupId: Long
) {

    val localTime: Long
        get() = time + zoneOffset

    fun toEventEntity() = EventEntity(id, name, time, zoneOffset, groupId)
}