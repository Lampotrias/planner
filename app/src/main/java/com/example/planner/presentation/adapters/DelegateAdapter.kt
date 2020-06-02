package com.example.planner.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


interface DelegateAdapter<VH : RecyclerView.ViewHolder, T> {
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    fun onBindViewHolder(holder: VH, items: List<T>, position: Int)
    fun onRecycled(holder: VH)
    fun isForViewType(items: List<*>, position: Int): Boolean
}
