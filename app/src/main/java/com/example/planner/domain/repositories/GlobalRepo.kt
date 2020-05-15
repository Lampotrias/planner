package com.example.planner.domain.repositories

import com.example.planner.domain.Subscriber

interface GlobalRepo {
    fun attach(s: Subscriber)
    fun deAttach()
    fun provideOffset(f: Float)
    fun provideState(state: Int)
}