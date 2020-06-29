package com.lamp.planner.domain.interactors

import com.lamp.planner.domain.Group
import com.lamp.planner.domain.Result
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.domain.repositories.RepoDb

import javax.inject.Inject

class CreateGroupInteractor @Inject constructor(
    private val repoDb: RepoDb
) : BaseUseCase<Group, Long>() {
    override suspend fun run(params: Group): Result<Failure, Long> {
        return repoDb.createGroup(params.toGroupEntity())
    }
}
