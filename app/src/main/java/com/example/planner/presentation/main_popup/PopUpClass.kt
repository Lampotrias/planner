package com.example.planner.presentation.main_popup

import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.example.planner.R


class PopUpClass {
    fun showPopupWindow(view: View) {
        val inflater =
            view.context.getSystemService(LAYOUT_INFLATER_SERVICE) as? LayoutInflater
        val popupView = inflater?.inflate(R.layout.popup_add_event, null)
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.MATCH_PARENT
        val focusable = true
        val popupWindow = PopupWindow(popupView, width, height, focusable)
        //popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        popupWindow.animationStyle = R.style.popup_window_animation_slide
        popupWindow.showAsDropDown(view, 1, 1)
        popupView?.setOnTouchListener { _, _ ->
            //popupWindow.dismiss()
            return@setOnTouchListener true
        }
    }
}