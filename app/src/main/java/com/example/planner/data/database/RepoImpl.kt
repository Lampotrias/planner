package com.example.planner.data.database

import com.example.planner.domain.repositories.EventRepo
import timber.log.Timber

class RepoImpl: EventRepo {
    override fun save(event: EventEntity) {
        Timber.i("ok save")
    }
}