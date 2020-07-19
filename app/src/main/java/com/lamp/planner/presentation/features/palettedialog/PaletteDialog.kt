package com.lamp.planner.presentation.features.palettedialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.GridLayoutManager
import com.lamp.planner.R
import com.lamp.planner.databinding.PaletteLayoutBinding
import com.lamp.planner.presentation.adapters.CompositeAdapter
import com.lamp.planner.presentation.adapters.ManagerImpl
import com.lamp.planner.presentation.adapters.PaletteDelegateAdapter
import com.lamp.planner.presentation.base.BaseDialog

class PaletteDialog : BaseDialog() {
    private lateinit var binding: PaletteLayoutBinding

    companion object {
        const val PALETTE_REQUEST_KEY = "PALETTE_DIALOG_RESULT"
        const val PALETTE_DIALOG_PARAM_COLOR = "PALETTE_DIALOG_PARAM_COLOR"
    }

    override fun onInitDependencyInjection() {
    }

    fun getColorsList(): List<Int> {
        val colorsRes = binding.root.resources.obtainTypedArray(R.array.all_colors)
        val end = colorsRes.length() - 1
        colorsRes.recycle()
        return (0..end).toList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PaletteLayoutBinding.inflate(inflater, container, false)

        binding.paletteRecycler.apply {
            val manager = ManagerImpl<Int>().also { it.addDelegate(PaletteDelegateAdapter()) }
            val paletteAdapter =
                CompositeAdapter(manager, object : CompositeAdapter.ClickItemInterface<Int> {
                    override fun onClick(item: Int, position: Int) {
                        close(item)
                    }
                })
            paletteAdapter.setItemList(getColorsList())
            adapter = paletteAdapter
            layoutManager = GridLayoutManager(context, 4)
        }

        return binding.root
    }

    fun close(color: Int) {
        setFragmentResult(
            PALETTE_REQUEST_KEY,
            bundleOf(PALETTE_DIALOG_PARAM_COLOR to color)
        )
        this.dialog?.dismiss()
    }
}
