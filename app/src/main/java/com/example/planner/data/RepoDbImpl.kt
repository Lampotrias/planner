package com.example.planner.data

import android.provider.ContactsContract
import com.example.planner.data.database.AppDatabase
import com.example.planner.data.database.EventEntity
import com.example.planner.data.database.GroupEntity
import com.example.planner.domain.Event
import com.example.planner.domain.Group
import com.example.planner.domain.Result
import com.example.planner.domain.Result.Error
import com.example.planner.domain.Result.Success
import com.example.planner.domain.excetion.Failure
import com.example.planner.domain.interactors.None
import com.example.planner.domain.repositories.RepoDb
import com.example.planner.extention.toEvent
import com.example.planner.extention.toGroup
import javax.inject.Inject

class RepoDbImpl @Inject constructor(private val appDatabase: AppDatabase) : RepoDb {

    override fun saveEvent(event: EventEntity): Result<Failure, Long> {
        return try {
            Success(appDatabase.eventDao().saveEvent(event))
        }catch (throwable: Throwable){
            Error(Failure.DatabaseErrorQuery(throwable.message))
        }
    }

    override fun getAllEvents(): Result<Failure, List<Event>> {
        return try {
            val result = appDatabase.eventDao().getAllEvents()
            return Success(result.toEvent())
        }catch (throwable: Throwable){
            Error(Failure.DatabaseErrorQuery(throwable.message))
        }
    }

    override fun getEventById(id: Long): Result<Failure, Event> {
        return try {
            val result = appDatabase.eventDao().getEventById(id)
            return Success(result.toEvent())
        }catch (throwable: Throwable){
            Error(Failure.DatabaseErrorQuery(throwable.message))
        }
    }

    override fun createGroup(groupEntity: GroupEntity): Result<Failure, Long> {
        return try {
            return Success(appDatabase.groupDao().createGroup(groupEntity))
        }catch (throwable: Throwable){
            Error(Failure.DatabaseErrorQuery(throwable.message))
        }
    }

    override fun getGroups(): Result<Failure, List<Group>> {
        return try {
            val result = appDatabase.groupDao().getGroups()
            return Success(result.toGroup())
        }catch (throwable: Throwable){
            Error(Failure.DatabaseErrorQuery(throwable.message))
        }
    }

    override fun setDefaultGroup(id: Long): Result<Failure, Int> {
        return try {
            val result = appDatabase.groupDao().setDefaultGroup(id)
            return Success(result)
        }catch (throwable: Throwable){
            Error(Failure.DatabaseErrorQuery(throwable.message))
        }
    }

}
