package com.lamp.planner.extention

import com.lamp.planner.data.database.EventEntity
import com.lamp.planner.data.database.GroupEntity
import com.lamp.planner.domain.Event
import com.lamp.planner.domain.Group

fun List<EventEntity>.toEvent(): List<Event> {
    val tpm: ArrayList<Event> = arrayListOf()
    this.forEach { tpm.add(it.toEvent()) }
    return tpm
}

fun List<GroupEntity>.toGroup(): List<Group> {
    val tpm: ArrayList<Group> = arrayListOf()
    this.forEach { tpm.add(it.toEvent()) }
    return tpm
}
