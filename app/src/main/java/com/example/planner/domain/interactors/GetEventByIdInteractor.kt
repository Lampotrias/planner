package com.example.planner.domain.interactors

import com.example.planner.domain.Event
import com.example.planner.domain.Result
import com.example.planner.domain.excetion.Failure
import com.example.planner.domain.repositories.RepoDb
import javax.inject.Inject

class GetEventByIdInteractor @Inject constructor(
    private val repoDb: RepoDb
): BaseUseCase<GetEventByIdInteractor.Params, Event>() {
    override suspend fun run(params: Params): Result<Failure, Event> {
        return repoDb.getEventById(params.id)
    }

    data class Params(val id: Long)

}