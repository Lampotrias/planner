package com.lamp.planner.domain.repositories

import com.lamp.planner.domain.Subscriber

interface GlobalRepo {
    fun attach(s: Subscriber)
    fun deAttach()
    fun provideOffset(f: Float)
    fun provideState(state: Int)
}
