package com.example.planner.presentation.features.my_day

import android.os.Bundle
import androidx.navigation.NavDirections
import com.example.planner.domain.Event
import com.example.planner.domain.excetion.Failure
import com.example.planner.presentation.features.my_day.adapter.EventModel
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

interface MyDayView: MvpView {
    @AddToEndSingle
    fun showAnimationFab(offset: Float)

    @AddToEnd
    fun showAddEventPopupDialog(navDirections: NavDirections)

    @Skip
    fun handleFailure(failure: Failure?)

    @AddToEnd
    fun showEventList(list: List<EventModel>)
}