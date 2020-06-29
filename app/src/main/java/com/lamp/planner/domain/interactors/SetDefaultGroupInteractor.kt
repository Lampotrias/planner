package com.lamp.planner.domain.interactors

import com.lamp.planner.domain.Result
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.domain.repositories.RepoDb
import javax.inject.Inject

class SetDefaultGroupInteractor @Inject constructor(val repoDb: RepoDb) : BaseUseCase<Long, Int>() {
    override suspend fun run(params: Long): Result<Failure, Int> {
        return repoDb.setDefaultGroup(params)
    }
}
