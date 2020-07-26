package com.lamp.planner.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lamp.planner.databinding.HolderEventListBinding
import com.lamp.planner.presentation.DisplayableItem
import com.lamp.planner.presentation.features.myday.model.EventModel
import java.text.SimpleDateFormat
import java.util.Date

class EventItemDelegateAdapter : DelegateAdapter<DisplayableItem> {
    private lateinit var layoutInflater: LayoutInflater
    private lateinit var binding: HolderEventListBinding

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        layoutInflater =
            LayoutInflater.from(parent.context)
                ?: throw kotlin.NullPointerException("layoutInflater error")
        binding = HolderEventListBinding.inflate(layoutInflater, parent, false)
        return EventHolder(binding)
    }

    override fun onBindViewHolder(
        items: List<DisplayableItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        isSelected: Boolean,
        payloads: MutableList<Any>
    ) {
        if (holder is EventHolder) {
            val item = items[position] as EventModel
            val format = SimpleDateFormat.getDateTimeInstance()
            holder.name = item.name
            holder.time = format.format(Date(item.time))
            holder.groupName = item.groupName
        }
    }

    override fun isForViewType(items: List<DisplayableItem>, position: Int): Boolean {
        return (items[position] is EventModel)
    }

    inner class EventHolder(private val binding: HolderEventListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var name: String
            get() = binding.itemName.text.toString()
            set(value) {
                binding.itemName.text = value
            }

        var time: String
            get() = binding.itemTime.text.toString()
            set(value) {
                binding.itemTime.text = value
            }

        var groupName: String
            get() = binding.groupName.text.toString()
            set(value) {
                binding.groupName.text = value
            }
    }
}
