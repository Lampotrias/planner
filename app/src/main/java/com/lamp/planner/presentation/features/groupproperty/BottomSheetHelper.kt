package com.lamp.planner.presentation.features.groupproperty

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.lamp.planner.R
import com.lamp.planner.databinding.FragmentGroupPropertyBinding

class BottomSheetHelper(
    private val binding: FragmentGroupPropertyBinding,
    private val bottomView: View
) : GroupPropertyBottom {
    override fun slideUp() {
        val animate = AnimationUtils.loadAnimation(bottomView.context, R.anim.slide_to_top)
        animate.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {}
            override fun onAnimationEnd(p0: Animation?) {}
            override fun onAnimationStart(p0: Animation?) {
                p0?.run {
                    bottomView.visibility = View.VISIBLE
                }
            }
        })
        bottomView.startAnimation(animate)
    }

    override fun slideDown() {
        val animate = AnimationUtils.loadAnimation(bottomView.context, R.anim.slide_to_bottom)
        animate.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {}
            override fun onAnimationEnd(p0: Animation?) {
                p0?.run {
                    bottomView.visibility = View.INVISIBLE
                }
            }

            override fun onAnimationStart(p0: Animation?) {}
        })
        bottomView.startAnimation(animate)
    }

    override fun setCallback(callback: GroupPropertyBottom.OnClickBottomButton) {
        binding.imgPalette.setOnClickListener { callback.clickPalette() }
        binding.imgImage.setOnClickListener { callback.clickImage() }
        binding.imgEdit.setOnClickListener { callback.clickEdit() }
        binding.imgDelete.setOnClickListener { callback.clickDelete() }
        binding.imgBookmark.setOnClickListener { callback.clickBookmark() }
    }

    override fun setSelectValue(value: Int) {
        binding.selectCounter.text = value.toString()
        setVisibilityOfCounter(value)
    }

    private fun setVisibilityOfCounter(value: Int) {
        if (value == 1) {
            binding.imgEdit.visibility = View.VISIBLE
            binding.imgBookmark.visibility = View.VISIBLE
        } else {
            binding.imgEdit.visibility = View.INVISIBLE
            binding.imgBookmark.visibility = View.INVISIBLE
        }
    }
}
