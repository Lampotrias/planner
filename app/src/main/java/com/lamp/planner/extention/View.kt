package com.lamp.planner.extention

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.children

fun View.getChildImageView(): ImageView? {
    val temp = this
    if (temp is ViewGroup) {
        val image = temp.children.filter { it is ImageView }.first()
        return image as ImageView
    }
    return null
}
