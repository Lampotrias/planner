package com.lamp.planner.domain.interactors

import com.lamp.planner.domain.Group
import com.lamp.planner.domain.Result
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.domain.repositories.RepoDb
import javax.inject.Inject

class GetGroupInteractor @Inject constructor(
    private val repoDb: RepoDb
) : BaseUseCase<Long, Group>() {
    override suspend fun run(params: Long): Result<Failure, Group> {
        return repoDb.getGroup(params)
    }
}
