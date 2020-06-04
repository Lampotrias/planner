package com.example.planner.presentation.features.main_screen.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.planner.databinding.GroupListHolderBinding

class GroupHolder(private val binding: GroupListHolderBinding) : RecyclerView.ViewHolder(binding.root){
    fun setPicture(picture: Int){
        binding.groupLogo.setImageResource(picture)
    }

    fun setGroupName(name: String){
        binding.groupName.text = name
    }
}