package com.lamp.planner.domain.interactors

import com.lamp.planner.domain.GroupPicture
import com.lamp.planner.domain.Result
import com.lamp.planner.domain.excetion.Failure
import com.lamp.planner.domain.repositories.RepoDb
import javax.inject.Inject

class GroupUpdatePictureInteractor @Inject constructor(
    private val repoDb: RepoDb
) : BaseUseCase<List<GroupPicture>, Int>() {
    override suspend fun run(params: List<GroupPicture>): Result<Failure, Int> {
        return repoDb.updateGroupPicture(params)
    }
}
