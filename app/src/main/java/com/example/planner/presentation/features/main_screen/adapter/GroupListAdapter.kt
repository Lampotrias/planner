package com.example.planner.presentation.features.main_screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.planner.databinding.GroupListHolderBinding
import com.example.planner.domain.Group

class GroupListAdapter(private val data: List<Group>): RecyclerView.Adapter<GroupHolder>() {

    private lateinit var binding: GroupListHolderBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupHolder {
        binding = GroupListHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroupHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: GroupHolder, position: Int) {
        holder.setPicture(data[position].picture)
        holder.setGroupName(data[position].name)
    }
}