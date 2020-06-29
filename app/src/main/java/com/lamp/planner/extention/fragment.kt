package com.lamp.planner.extention

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

fun Fragment.navigate(view: FragmentContainerView, resId: Int, bundle: Bundle? = null) {
    view.findNavController().navigate(resId, bundle)
}

fun Fragment.navigate(navDirections: NavDirections) {
    this.findNavController().navigate(navDirections)
}
