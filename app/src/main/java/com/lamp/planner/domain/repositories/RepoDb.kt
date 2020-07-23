package com.lamp.planner.domain.repositories

import com.lamp.planner.data.database.EventEntity
import com.lamp.planner.data.database.GroupEntity
import com.lamp.planner.domain.Event
import com.lamp.planner.domain.Group
import com.lamp.planner.domain.GroupColor
import com.lamp.planner.domain.GroupImage
import com.lamp.planner.domain.Result
import com.lamp.planner.domain.excetion.Failure

interface RepoDb {
    fun saveEvent(event: EventEntity): Result<Failure, Long>
    fun getAllEvents(): Result<Failure, List<Event>>
    fun getEventById(id: Long): Result<Failure, Event>

    fun createGroup(groupEntity: GroupEntity): Result<Failure, Long>
    fun getGroups(): Result<Failure, List<Group>>
    fun getGroup(id: Long): Result<Failure, Group>
    fun updateGroupColor(groupColors: List<GroupColor>): Result<Failure, Int>
    fun updateGroupPicture(groupImages: List<GroupImage>): Result<Failure, Int>
    fun deleteGroups(groupIds: List<Long>): Result<Failure, Int>
    fun setDefaultGroup(id: Long): Result<Failure, Int>
}
