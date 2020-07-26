package com.lamp.planner.presentation.adapters

import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.util.contains
import androidx.core.util.isEmpty
import androidx.recyclerview.widget.RecyclerView
import com.lamp.planner.R
import com.lamp.planner.databinding.HolderImageSelectorBinding

class ImageSelectorDelegateAdapter : DelegateAdapter<Int> {
    private lateinit var layoutInflater: LayoutInflater
    private lateinit var binding: HolderImageSelectorBinding
    private var imagesMap = SparseIntArray()

    private fun initImagesMap() {
        val imagesRes = binding.root.resources.obtainTypedArray(R.array.all_images)
        val end = imagesRes.length() - 1
        for (key: Int in 0..end) {
            imagesMap.put(
                key, imagesRes.getResourceId(key, -1)
            )
        }
        imagesRes.recycle()
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        layoutInflater = LayoutInflater.from(parent.context)
            ?: throw kotlin.NullPointerException("layoutInflater error")
        binding =
            HolderImageSelectorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        if (imagesMap.isEmpty()) {
            initImagesMap()
        }
        return ImageSelectorHolder(binding)
    }

    override fun onBindViewHolder(
        items: List<Int>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        isSelected: Boolean,
        payloads: MutableList<Any>
    ) {
        if (holder is ImageSelectorHolder) {
            val image = items[position]
            if (imagesMap.contains(image)) {
                holder.setImage(imagesMap[image])
            } else {
                throw kotlin.IllegalArgumentException("wrong image $image. Exists images: $imagesMap")
            }
        }
    }

    override fun isForViewType(items: List<Int>, position: Int) = true

    inner class ImageSelectorHolder(binding: HolderImageSelectorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setImage(image: Int) {
            binding.imageSelectorBase.setImageResource(image)
        }
    }
}
