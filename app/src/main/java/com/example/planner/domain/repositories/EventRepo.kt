package com.example.planner.domain.repositories

import com.example.planner.data.database.EventEntity
import com.example.planner.domain.Event
import com.example.planner.domain.Result
import com.example.planner.domain.excetion.Failure
import java.sql.Timestamp


interface EventRepo {
    fun saveEvent(event: EventEntity): Result<Failure, Long>
    fun getAllEvents(): Result<Failure, List<Event>>
    fun getEventById(id: Long): Result<Failure, Event>
}