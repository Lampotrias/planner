package com.lamp.planner.data

import com.lamp.planner.domain.Subscriber
import com.lamp.planner.domain.repositories.GlobalRepo
import javax.inject.Singleton

@Singleton
class GlobalRepoImpl : GlobalRepo {

    private var subscriber: Subscriber? = null

    override fun attach(s: Subscriber) {
        subscriber = s
    }

    override fun deAttach() {
        subscriber = null
    }

    override fun provideOffset(f: Float) {
        subscriber?.provideOffset(f)
    }

    override fun provideState(state: Int) {
        subscriber?.provideState(state)
    }
}
