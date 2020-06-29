package com.lamp.planner.domain.interactors

import com.lamp.planner.domain.Event
import com.lamp.planner.domain.Result
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.domain.repositories.RepoDb
import javax.inject.Inject

class GetAllEventsInteractor @Inject constructor(
    private val repoDb: RepoDb
) : BaseUseCase<None, List<Event>>() {
    override suspend fun run(params: None): Result<Failure, List<Event>> {
        return repoDb.getAllEvents()
    }
}
