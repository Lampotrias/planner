package com.example.planner.extention

import com.example.planner.data.database.EventEntity
import com.example.planner.domain.Event

fun List<EventEntity>.toEvent(): List<Event> {
    val tpm: ArrayList<Event> = arrayListOf()
    this.forEach { tpm.add(it.toEvent()) }
    return tpm
}