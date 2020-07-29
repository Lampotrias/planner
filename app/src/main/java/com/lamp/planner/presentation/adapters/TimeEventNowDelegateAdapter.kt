package com.lamp.planner.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lamp.planner.databinding.HolderEventNowListBinding
import com.lamp.planner.presentation.DisplayableItem
import com.lamp.planner.presentation.features.myday.model.TimeNowModel

class TimeEventNowDelegateAdapter : DelegateAdapter<DisplayableItem> {
    private lateinit var layoutInflater: LayoutInflater
    private lateinit var binding: HolderEventNowListBinding

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        layoutInflater = LayoutInflater.from(parent.context)
            ?: throw kotlin.NullPointerException("layoutInflater error")
        binding = HolderEventNowListBinding.inflate(layoutInflater, parent, false)
        return TimeNowHolder(binding)
    }

    override fun onBindViewHolder(
        items: List<DisplayableItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        isSelected: Boolean,
        payloads: MutableList<Any>
    ) {
        if (holder is TimeNowHolder) {
            val item = items[position] as TimeNowModel
            holder.title = item.nowTime
        }
    }

    override fun isForViewType(items: List<DisplayableItem>, position: Int) =
        (items[position] is TimeNowModel)

    inner class TimeNowHolder(private val binding: HolderEventNowListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var title: String
            get() = binding.timeNow.text.toString()
            set(value) {
                binding.timeNow.text = value
            }
    }
}
