package com.example.planner.presentation.features.main_screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.planner.databinding.EventListHolderBinding
import com.example.planner.databinding.GroupListHolderBinding
import com.example.planner.domain.Group
import com.example.planner.presentation.adapters.DelegateAdapter
import com.example.planner.presentation.features.my_day.adapter.EventItemDelegateAdapter
import com.example.planner.presentation.features.my_day.adapter.EventModel
import java.text.SimpleDateFormat
import java.util.*

class GroupDelegateAdapter : DelegateAdapter<Group> {
    private lateinit var layoutInflater: LayoutInflater
    private lateinit var binding: GroupListHolderBinding

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        layoutInflater = LayoutInflater.from(parent.context) ?: throw kotlin.NullPointerException("layoutInflater error")
        binding = GroupListHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroupHolder(binding)
    }

    override fun onBindViewHolder(
        items: List<Group>,
        position: Int,
        holder: RecyclerView.ViewHolder
    ) {
        if (holder is GroupDelegateAdapter.GroupHolder){
            val item = items[position] as Group
            val format = SimpleDateFormat.getDateTimeInstance()
            holder.groupName = item.name
            holder.groupLogo = item.picture
        }
    }

    override fun isForViewType(items: List<Group>, position: Int): Boolean {
        return (items[position] is Group)
    }

    inner class GroupHolder(private val binding: GroupListHolderBinding) : RecyclerView.ViewHolder(binding.root) {
        var groupName: String
            get() = binding.groupName.text.toString()
            set(value) {binding.groupName.text = value}

        var groupLogo: Int = 0
            set(value) {binding.groupLogo.setImageResource(value)}
    }
}