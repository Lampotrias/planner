package com.example.planner.presentation.my_day

import androidx.core.os.bundleOf
import com.example.planner.domain.Subscriber
import com.example.planner.domain.interactors.GlobalInteractor
import com.example.planner.presentation.base.BasePresenter
import timber.log.Timber
import javax.inject.Inject

class MyDayPresenter @Inject constructor(private var globalInteractor: GlobalInteractor): BasePresenter<MyDayView>(), Subscriber {

    fun setResultFromDialogEventAdd(event: EventTransferObject){

    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        globalInteractor.attach(this)
    }

    override fun provideState(state: Int) {
        Timber.e("state= %s", state.toString())
    }

    override fun provideOffset(offset: Float) {
        viewState.showAnimationFab(offset)
        Timber.e("Offset= %s", offset.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        globalInteractor.deAttach()
    }

    fun showPopupDialog(){
        val directions = MyDayFragmentDirections.actionMyDayToEventDialog(null)
        viewState.showAddEventPopupDialog(directions)
    }

    fun saveEvent(event: EventTransferObject) {
        Timber.tag("result").e(event.toString())
    }


}