package com.lamp.planner.presentation.features.mainpopup

//import android.content.Context.LAYOUT_INFLATER_SERVICE
//import android.view.Gravity
//import android.view.LayoutInflater
//import android.view.View
//import android.widget.LinearLayout
//import android.widget.PopupWindow
//import androidx.recyclerview.widget.GridLayoutManager
//import com.lamp.planner.R
//import com.lamp.planner.databinding.PaletteLayoutBinding
//import com.lamp.planner.domain.Constants
//import com.lamp.planner.presentation.adapters.CompositeAdapter
//import com.lamp.planner.presentation.adapters.ManagerImpl
//import com.lamp.planner.presentation.adapters.PaletteDelegateAdapter

// class PalettePopup(private val anchorView: View, callback: ClickPalette) {
//    private val context by lazy { anchorView.context }
//    private val binding by lazy {
//        val inflater =
//            context.getSystemService(LAYOUT_INFLATER_SERVICE) as? LayoutInflater
//        PaletteLayoutBinding.inflate(inflater!!)
//    }
//
//    init {
//        binding.root.setBackgroundResource(R.drawable.dialog_rounded_bg)
//        binding.paletteRecycler.apply {
//            val manager = ManagerImpl<Int>().also { it.addDelegate(PaletteDelegateAdapter()) }
//            val paletteAdapter =
//                CompositeAdapter(manager, object : CompositeAdapter.ClickItemInterface<Int> {
//                    override fun onClick(item: Int, position: Int) {
//                        callback.onClick(item)
//                        popupWindow.dismiss()
//                    }
//                })
//            paletteAdapter.setItemList(listOf(0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2))
//            adapter = paletteAdapter
//            layoutManager = GridLayoutManager(context, 4)
//        }
//    }
//
//    private val popupWindow = PopupWindow(
//        binding.root,
//        (anchorView.resources.displayMetrics.widthPixels * Constants.WIDTH_DIALOG_FLOAT).toInt(),
//        LinearLayout.LayoutParams.WRAP_CONTENT,
//        true
//    )
//
//    fun show() {
//        popupWindow
//        popupWindow.animationStyle = R.style.popup_window_animation_slide
//        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0)
//    }
//
//    interface ClickPalette {
//        fun onClick(color: Int)
//    }
//}
