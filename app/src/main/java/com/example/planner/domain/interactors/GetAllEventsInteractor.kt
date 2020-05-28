package com.example.planner.domain.interactors

import com.example.planner.domain.Event
import com.example.planner.domain.Result
import com.example.planner.domain.excetion.Failure
import com.example.planner.domain.repositories.EventRepo
import javax.inject.Inject

class GetAllEventsInteractor @Inject constructor(
    private val repo: EventRepo
) : BaseUseCase<None, List<Event>>() {
    override suspend fun run(params: None): Result<Failure, List<Event>> {
        return repo.getAllEvents()
    }
}