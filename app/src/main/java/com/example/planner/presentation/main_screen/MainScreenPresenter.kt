package com.example.planner.presentation.main_screen

import com.example.planner.data.database.EventEntity
import com.example.planner.domain.repositories.EventRepo
import com.example.planner.domain.interactors.GlobalInteractor
import com.example.planner.presentation.base.BasePresenter
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class MainScreenPresenter @Inject constructor(private val repo: EventRepo): BasePresenter<MainScreenView>() {

    @Inject lateinit var globalInteractor: GlobalInteractor

    override fun onFirstViewAttach() {
        val event = EventEntity(1,"2", 1,1,1,1,1)
        repo.save(event)

        viewState.openBottomSheet()
        super.onFirstViewAttach()
    }

    fun setOffset(offset: Float){
        globalInteractor.setOffset(offset)
    }

    fun setSlidingState(state: Int){
        globalInteractor.setSlidingState(state)
    }
}