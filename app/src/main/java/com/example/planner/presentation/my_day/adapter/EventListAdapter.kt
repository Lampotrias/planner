package com.example.planner.presentation.my_day.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.planner.databinding.EventListItemBinding
import com.example.planner.domain.Event

class EventListAdapter (private val data: List<EventModel>): RecyclerView.Adapter<EventHolder>() {

    private lateinit var binding: EventListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        binding = EventListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.setName(data[position].name)
        holder.setTime(data[position].time)
    }
}