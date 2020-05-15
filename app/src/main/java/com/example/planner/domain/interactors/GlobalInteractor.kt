package com.example.planner.domain.interactors

import com.example.planner.domain.Subscriber
import com.example.planner.domain.repositories.GlobalRepo
import javax.inject.Inject

class GlobalInteractor @Inject constructor(private val globalRepo: GlobalRepo) {

    fun attach(s: Subscriber){
        globalRepo.attach(s)
    }

    fun deAttach(){
        globalRepo.deAttach()
    }

    fun setOffset(offset: Float) {
        globalRepo.provideOffset(offset)
    }

    fun setSlidingState(state: Int) {
        globalRepo.provideState(state)
    }
}