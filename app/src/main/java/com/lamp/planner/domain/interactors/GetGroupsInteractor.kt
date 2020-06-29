package com.lamp.planner.domain.interactors

import com.lamp.planner.domain.Group
import com.lamp.planner.domain.Result
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.domain.repositories.RepoDb
import javax.inject.Inject

class GetGroupsInteractor @Inject constructor(
    private val repoDb: RepoDb
) : BaseUseCase<None, List<Group>>() {
    override suspend fun run(params: None): Result<Failure, List<Group>> {
        return repoDb.getGroups()
    }
}
