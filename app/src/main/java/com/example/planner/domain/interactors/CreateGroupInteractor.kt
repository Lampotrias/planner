package com.example.planner.domain.interactors

import com.example.planner.domain.Group
import com.example.planner.domain.Result
import com.example.planner.domain.excetion.Failure
import com.example.planner.domain.repositories.RepoDb

import javax.inject.Inject

class CreateGroupInteractor @Inject constructor(
    private val repoDb: RepoDb
) : BaseUseCase<Group, Long>() {
    override suspend fun run(params: Group): Result<Failure, Long> {
        return repoDb.createGroup(params.toGroupEntity())
    }
}