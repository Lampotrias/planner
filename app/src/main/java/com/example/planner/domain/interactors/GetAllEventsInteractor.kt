package com.example.planner.domain.interactors

import com.example.planner.domain.Event
import com.example.planner.domain.Result
import com.example.planner.domain.excetion.Failure
import com.example.planner.domain.repositories.RepoDb
import javax.inject.Inject

class GetAllEventsInteractor @Inject constructor(
    private val repoDb: RepoDb
) : BaseUseCase<None, List<Event>>() {
    override suspend fun run(params: None): Result<Failure, List<Event>> {
        return repoDb.getAllEvents()
    }
}