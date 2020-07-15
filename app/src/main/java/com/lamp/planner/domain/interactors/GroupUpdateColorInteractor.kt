package com.lamp.planner.domain.interactors

import com.lamp.planner.domain.GroupColor
import com.lamp.planner.domain.Result
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.domain.repositories.RepoDb
import javax.inject.Inject

class GroupUpdateColorInteractor @Inject constructor(
    private val repoDb: RepoDb
) : BaseUseCase<List<GroupColor>, Int>() {
    override suspend fun run(params: List<GroupColor>): Result<Failure, Int> {
        return repoDb.updateGroupColor(params)
    }
}
