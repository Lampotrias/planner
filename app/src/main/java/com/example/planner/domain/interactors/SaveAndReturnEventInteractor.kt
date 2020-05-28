package com.example.planner.domain.interactors

import com.example.planner.domain.Event
import com.example.planner.domain.Result
import com.example.planner.domain.excetion.Failure
import com.example.planner.domain.repositories.EventRepo
import javax.inject.Inject

class SaveAndReturnEventInteractor @Inject constructor(
    private val repo: EventRepo
) : BaseUseCase<Event, Event>() {

    override suspend fun run(params: Event): Result<Failure, Event> {
        return when(val result = repo.saveEvent(params.toEventEntity())){
            is Result.Error -> result
            is Result.Success -> getEvent(result)
        }
    }

    private fun getEvent(result: Result.Success<Long>): Result<Failure, Event>{
        return repo.getEventById(result.b)
    }
}


