package com.example.planner.presentation.my_day.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.planner.databinding.EventListItemBinding

class EventHolder(private val binding: EventListItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun setName(name: String){
        binding.itemNameEvent.text = name
    }

    fun setTime(time: String){
        binding.itemTime.text = time
    }
}