package com.lamp.planner.data

import com.lamp.planner.data.database.AppDatabase
import com.lamp.planner.data.database.EventEntity
import com.lamp.planner.data.database.GroupEntity
import com.lamp.planner.domain.Event
import com.lamp.planner.domain.Group
import com.lamp.planner.domain.GroupColor
import com.lamp.planner.domain.GroupPicture
import com.lamp.planner.domain.Result
import com.lamp.planner.domain.Result.Error
import com.lamp.planner.domain.Result.Success
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.domain.repositories.RepoDb
import com.lamp.planner.extention.toEvent
import com.lamp.planner.extention.toGroup
import javax.inject.Inject

class RepoDbImpl @Inject constructor(private val appDatabase: AppDatabase) : RepoDb {

    override fun saveEvent(event: EventEntity): Result<Failure, Long> {
        return try {
            Success(appDatabase.eventDao().saveEvent(event))
        } catch (throwable: Throwable) {
            Error(Failure.DatabaseErrorQuery(throwable.message))
        }
    }

    override fun getAllEvents(): Result<Failure, List<Event>> {
        return try {
            val result = appDatabase.eventDao().getAllEvents()
            return Success(result.toEvent())
        } catch (throwable: Throwable) {
            Error(Failure.DatabaseErrorQuery(throwable.message))
        }
    }

    override fun getEventById(id: Long): Result<Failure, Event> {
        return try {
            val result = appDatabase.eventDao().getEventById(id)
            return Success(result.toEvent())
        } catch (throwable: Throwable) {
            Error(Failure.DatabaseErrorQuery(throwable.message))
        }
    }

    override fun createGroup(groupEntity: GroupEntity): Result<Failure, Long> {
        return try {
            return Success(appDatabase.groupDao().createGroup(groupEntity))
        } catch (throwable: Throwable) {
            Error(Failure.DatabaseErrorQuery(throwable.message))
        }
    }

    override fun getGroups(): Result<Failure, List<Group>> {
        return try {
            val result = appDatabase.groupDao().getGroups()
            return Success(result.toGroup())
        } catch (throwable: Throwable) {
            Error(Failure.DatabaseErrorQuery(throwable.message))
        }
    }

    override fun updateGroupColor(groupColors: List<GroupColor>): Result<Failure, Int> {
        return try {
            var counter = 0
            groupColors.forEach {
                val result = appDatabase.groupDao().updateColor(it.id, it.color)
                counter += result
            }
            return Success(counter)
        } catch (throwable: Throwable) {
            Error(Failure.DatabaseErrorQuery(throwable.message))
        }
    }

    override fun updateGroupPicture(groupPictures: List<GroupPicture>): Result<Failure, Int> {
        return try {
            var counter = 0
            groupPictures.forEach {
                val result = appDatabase.groupDao().updatePicture(it.id, it.picture)
                counter += result
            }
            return Success(counter)
        } catch (throwable: Throwable) {
            Error(Failure.DatabaseErrorQuery(throwable.message))
        }
    }

    override fun deleteGroups(groupIds: List<Long>): Result<Failure, Int> {
        return try {
            val result = appDatabase.groupDao().deleteGroups(groupIds)
            return Success(result)
        } catch (throwable: Throwable) {
            Error(Failure.DatabaseErrorQuery(throwable.message))
        }
    }

    override fun setDefaultGroup(id: Long): Result<Failure, Int> {
        return try {
            val result = appDatabase.groupDao().setDefaultGroup(id)
            return Success(result)
        } catch (throwable: Throwable) {
            Error(Failure.DatabaseErrorQuery(throwable.message))
        }
    }
}
