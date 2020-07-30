package com.lamp.planner.presentation.features

import android.content.Context
import com.lamp.planner.R

object ImagesTools {
    fun getImageById(context: Context, imageId: Int): Int {
        val imagesRes = context.resources.obtainTypedArray(R.array.all_images)
        val imageResId = imagesRes.getResourceId(imageId, -1)
        imagesRes.recycle()
        return imageResId
    }
}
