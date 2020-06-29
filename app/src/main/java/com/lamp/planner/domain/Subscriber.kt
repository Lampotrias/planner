package com.lamp.planner.domain

interface Subscriber {
    fun provideState(state: Int)
    fun provideOffset(offset: Float)
}
