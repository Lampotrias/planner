package com.example.planner.data

import com.example.planner.domain.Subscriber
import com.example.planner.domain.repositories.GlobalRepo
import javax.inject.Singleton

@Singleton
class GlobalRepoImpl: GlobalRepo {

    private var subscriber: Subscriber? = null

    private var offset = 0f
    private var slidingState = 0

    override fun attach(s: Subscriber){
        subscriber = s
    }

    override fun deAttach(){
        subscriber = null
    }
    override fun provideOffset(f: Float) {
        subscriber?.provideOffset(f)
    }

    override fun provideState(state: Int) {
        subscriber?.provideState(state)
    }
}