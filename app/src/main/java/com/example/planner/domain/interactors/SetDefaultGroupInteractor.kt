package com.example.planner.domain.interactors

import com.example.planner.domain.Result
import com.example.planner.domain.excetion.Failure
import com.example.planner.domain.repositories.RepoDb
import javax.inject.Inject

class SetDefaultGroupInteractor @Inject constructor(val repoDb: RepoDb): BaseUseCase<Long, Int>() {
    override suspend fun run(params: Long): Result<Failure, Int> {
        return repoDb.setDefaultGroup(params)
    }
}