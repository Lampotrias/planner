package com.example.planner.presentation.features.main_screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.planner.databinding.GroupListHolderBinding
import com.example.planner.domain.Group
import com.example.planner.presentation.adapters.DelegateAdapter

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
        holder: RecyclerView.ViewHolder,
        isSelected: Boolean
    ) {
        if (holder is GroupDelegateAdapter.GroupHolder) {
            val item = items[position]
            holder.apply {
                groupName = item.name
                groupLogo = item.picture
                setSelected(isSelected)
            }
        }
    }

    override fun isForViewType(items: List<Group>, position: Int): Boolean {
        return true
    }

    inner class GroupHolder(private val binding: GroupListHolderBinding) : RecyclerView.ViewHolder(binding.root) {
        var groupName: String
            get() = binding.groupName.text.toString()
            set(value) {
                binding.groupName.text = value
            }

        var groupLogo: Int = 0
            set(value) {
                binding.groupLogo.setImageResource(value)
            }

        fun setSelected(selected: Boolean) {
            binding.groupLogo.alpha = if (selected) 1f else 0.3f
        }
    }
}