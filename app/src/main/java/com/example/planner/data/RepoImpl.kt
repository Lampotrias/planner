package com.example.planner.data

import com.example.planner.data.database.AppDatabase
import com.example.planner.data.database.EventEntity
import com.example.planner.domain.Event
import com.example.planner.domain.Result
import com.example.planner.domain.Result.Error
import com.example.planner.domain.Result.Success
import com.example.planner.domain.excetion.Failure
import com.example.planner.domain.repositories.EventRepo
import com.example.planner.extention.toEvent
import javax.inject.Inject

class RepoImpl @Inject constructor(private val appDatabase: AppDatabase) : EventRepo {

    override fun saveEvent(event: EventEntity): Result<Failure, Long> {
        return try {
            Success(appDatabase.eventDao().saveEvent(event))
        }catch (throwable: Throwable){
            Error(Failure.DatabaseErrorQuery)
        }
    }

    override fun getAllEvents(): Result<Failure, List<Event>> {
        return try {
            val result = appDatabase.eventDao().getAllEvents()
            return Success(result.toEvent())
        }catch (throwable: Throwable){
            Error(Failure.DatabaseErrorQuery)
        }
    }

    override fun getEventById(id: Long): Result<Failure, Event> {
        return try {
            val result = appDatabase.eventDao().getEventById(id)
            return Success(result.toEvent())
        }catch (throwable: Throwable){
            Error(Failure.DatabaseErrorQuery)
        }
    }
}
