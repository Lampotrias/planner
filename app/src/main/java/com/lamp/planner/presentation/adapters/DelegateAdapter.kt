package com.lamp.planner.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface DelegateAdapter<T> {
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun onBindViewHolder(
        items: List<T>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        isSelected: Boolean = false,
        payloads: MutableList<Any>
    )

    fun isForViewType(items: List<T>, position: Int): Boolean
}
