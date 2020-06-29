package com.lamp.planner.domain.interactors

import com.lamp.planner.domain.Event
import com.lamp.planner.domain.Result
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.domain.repositories.RepoDb
import javax.inject.Inject

class SaveAndReturnEventInteractor @Inject constructor(
    private val repoDb: RepoDb
) : BaseUseCase<Event, Event>() {

    override suspend fun run(params: Event): Result<Failure, Event> {
        return when (val result = repoDb.saveEvent(params.toEventEntity())) {
            is Result.Error -> result
            is Result.Success -> getEvent(result)
        }
    }

    private fun getEvent(result: Result.Success<Long>): Result<Failure, Event> {
        return repoDb.getEventById(result.b)
    }
}
