package com.lamp.planner.extention

import android.graphics.Color
import android.view.View
import android.widget.ImageView

fun ImageView.activate() {
    this.setColorFilter(Color.RED)
}

fun ImageView.inActive() {
    this.setColorFilter(Color.BLACK)
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}
