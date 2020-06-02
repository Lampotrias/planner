package com.example.planner.domain.interactors

import com.example.planner.domain.Event
import com.example.planner.domain.Result
import com.example.planner.domain.excetion.Failure
import com.example.planner.domain.repositories.RepoDb
import javax.inject.Inject

class SaveAndReturnEventInteractor @Inject constructor(
    private val repoDb: RepoDb
) : BaseUseCase<Event, Event>() {

    override suspend fun run(params: Event): Result<Failure, Event> {
        return when(val result = repoDb.saveEvent(params.toEventEntity())){
            is Result.Error -> result
            is Result.Success -> getEvent(result)
        }
    }

    private fun getEvent(result: Result.Success<Long>): Result<Failure, Event>{
        return repoDb.getEventById(result.b)
    }
}


