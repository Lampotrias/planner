package com.example.planner.presentation.features.main_screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.planner.databinding.EventListItemBinding
import com.example.planner.databinding.GroupListItemBinding
import com.example.planner.domain.Group
import com.example.planner.presentation.features.my_day.adapter.EventHolder
import com.example.planner.presentation.features.my_day.adapter.EventModel

class GroupListAdapter(private val data: List<Group>): RecyclerView.Adapter<GroupHolder>() {

    private lateinit var binding: GroupListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupHolder {
        binding = GroupListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroupHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: GroupHolder, position: Int) {
        holder.setPicture(data[position].picture)
        holder.setGroupName(data[position].name)
    }
}