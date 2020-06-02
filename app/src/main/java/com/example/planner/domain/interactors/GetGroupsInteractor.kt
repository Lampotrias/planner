package com.example.planner.domain.interactors

import com.example.planner.domain.Group
import com.example.planner.domain.Result
import com.example.planner.domain.excetion.Failure
import com.example.planner.domain.repositories.RepoDb
import javax.inject.Inject

class GetGroupsInteractor @Inject constructor(
    private val repoDb: RepoDb
) : BaseUseCase<None, List<Group>>() {
    override suspend fun run(params: None): Result<Failure, List<Group>> {
        return repoDb.getGroups()
    }

}