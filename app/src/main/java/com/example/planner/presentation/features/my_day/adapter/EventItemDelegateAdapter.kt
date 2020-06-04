package com.example.planner.presentation.features.my_day.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.planner.databinding.EventListHolderBinding
import com.example.planner.databinding.EventTimeListHolderBinding
import com.example.planner.presentation.DisplayableItem
import com.example.planner.presentation.adapters.DelegateAdapter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class EventItemDelegateAdapter : DelegateAdapter<DisplayableItem> {
    private lateinit var layoutInflater: LayoutInflater
    private lateinit var binding: EventListHolderBinding

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder{
        layoutInflater = LayoutInflater.from(parent.context) ?: throw kotlin.NullPointerException("layoutInflater error")
        binding = EventListHolderBinding.inflate(layoutInflater, parent, false)
        return EventHolder(binding)
    }

    override fun onBindViewHolder(
        items: List<DisplayableItem>,
        position: Int,
        holder: RecyclerView.ViewHolder
    ) {
        if (holder is EventHolder){
            val item = items[position] as EventModel
            val format = SimpleDateFormat.getDateTimeInstance()
            holder.name = item.name
            holder.time = format.format(Date(item.time))
        }
    }

    override fun isForViewType(items: List<DisplayableItem>, position: Int): Boolean {
        return (items[position] is EventModel)
    }

    inner class EventHolder(private val binding: EventListHolderBinding) : RecyclerView.ViewHolder(binding.root) {
        var name: String
            get() = binding.itemName.text.toString()
            set(value) {binding.itemName.text = value}

        var time: String
            get() = binding.itemTime.text.toString()
            set(value) {binding.itemTime.text = value}
    }
}