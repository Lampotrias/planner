package com.example.planner.domain.repositories

import com.example.planner.data.database.EventEntity
import com.example.planner.data.database.GroupEntity
import com.example.planner.domain.Event
import com.example.planner.domain.Group
import com.example.planner.domain.Result
import com.example.planner.domain.excetion.Failure
import com.example.planner.domain.interactors.None
import java.sql.Timestamp


interface RepoDb {
    fun saveEvent(event: EventEntity): Result<Failure, Long>
    fun getAllEvents(): Result<Failure, List<Event>>
    fun getEventById(id: Long): Result<Failure, Event>

    fun createGroup(groupEntity: GroupEntity): Result<Failure, Long>
    fun getGroups(): Result<Failure, List<Group>>
    fun setDefaultGroup(id: Long): Result<Failure, Int>
}