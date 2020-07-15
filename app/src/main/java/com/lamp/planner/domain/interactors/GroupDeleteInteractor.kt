package com.lamp.planner.domain.interactors

import com.lamp.planner.domain.Result
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.domain.repositories.RepoDb
import javax.inject.Inject

class GroupDeleteInteractor @Inject constructor(
    private val repoDb: RepoDb
) : BaseUseCase<List<Long>, Int>() {
    override suspend fun run(params: List<Long>): Result<Failure, Int> {
        return repoDb.deleteGroups(params)
    }
}
