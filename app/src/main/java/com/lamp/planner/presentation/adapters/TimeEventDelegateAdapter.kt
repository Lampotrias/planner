package com.lamp.planner.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lamp.planner.databinding.HolderEventTimeListBinding
import com.lamp.planner.presentation.DisplayableItem
import com.lamp.planner.presentation.features.myday.model.TimeEventModel

class TimeEventDelegateAdapter : DelegateAdapter<DisplayableItem> {
    private lateinit var layoutInflater: LayoutInflater
    private lateinit var binding: HolderEventTimeListBinding

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        layoutInflater = LayoutInflater.from(parent.context)
            ?: throw kotlin.NullPointerException("layoutInflater error")
        binding = HolderEventTimeListBinding.inflate(layoutInflater, parent, false)
        return TimeHolder(binding)
    }

    override fun onBindViewHolder(
        items: List<DisplayableItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        isSelected: Boolean,
        payloads: MutableList<Any>
    ) {
        if (holder is TimeHolder) {
            val item = items[position] as TimeEventModel
            holder.title = item.time
        }
    }

    override fun isForViewType(items: List<DisplayableItem>, position: Int) =
        (items[position] is TimeEventModel)

    inner class TimeHolder(private val binding: HolderEventTimeListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var title: String
            get() = binding.textView.text.toString()
            set(value) {
                binding.textView.text = value
            }
    }
}
