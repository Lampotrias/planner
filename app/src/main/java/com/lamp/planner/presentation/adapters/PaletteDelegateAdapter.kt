package com.lamp.planner.presentation.adapters

import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.util.contains
import androidx.core.util.isEmpty
import androidx.recyclerview.widget.RecyclerView
import com.lamp.planner.R
import com.lamp.planner.databinding.PaletteHolderBinding

class PaletteDelegateAdapter : DelegateAdapter<Int> {
    private lateinit var layoutInflater: LayoutInflater
    private lateinit var binding: PaletteHolderBinding
    private var colorMap = SparseIntArray()

    private fun initColorMap() {
        val colorsRes = binding.root.resources.obtainTypedArray(R.array.all_colors)
        val end = colorsRes.length() - 1
        for (key: Int in 0..end) {
            colorMap.put(
                key,
                ContextCompat.getColor(binding.root.context, colorsRes.getResourceId(key, -1))
            )
        }
        colorsRes.recycle()
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        layoutInflater = LayoutInflater.from(parent.context)
            ?: throw kotlin.NullPointerException("layoutInflater error")
        binding = PaletteHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        if (colorMap.isEmpty()) {
            initColorMap()
        }
        return PaletteHolder(binding)
    }

    override fun onBindViewHolder(
        items: List<Int>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        isSelected: Boolean,
        payloads: MutableList<Any>
    ) {
        if (holder is PaletteHolder) {
            val color = items[position]
            if (colorMap.contains(color)) {
                holder.setColor(colorMap[color])
            } else {
                throw kotlin.IllegalArgumentException("wrong color $color. Exists colors: $colorMap")
            }
        }
    }

    override fun isForViewType(items: List<Int>, position: Int) = true

    inner class PaletteHolder(binding: PaletteHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setColor(color: Int) {
            binding.paletteTestColor.setColorFilter(color)
            binding.paletteTestColor.alpha = 0.7f
        }
    }
}
