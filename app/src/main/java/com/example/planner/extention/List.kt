package com.example.planner.extention

import com.example.planner.data.database.EventEntity
import com.example.planner.data.database.GroupEntity
import com.example.planner.domain.Event
import com.example.planner.domain.Group

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