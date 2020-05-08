package com.example.planner.presentation.main_screen

import androidx.lifecycle.ViewModel
import com.example.planner.domain.BottomSheetControl
import javax.inject.Inject

class MainViewModel @Inject constructor(): ViewModel(), BottomSheetControl {

    var offset: Float = 0f
    var state: Int = 0

    override fun state(): Int = state
    override fun offset(): Float = offset

}