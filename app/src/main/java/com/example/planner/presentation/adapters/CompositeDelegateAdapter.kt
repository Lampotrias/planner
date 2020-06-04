package com.example.planner.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class CompositeAdapter<T>(private val manager: Manager<T>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<T>()

    override fun getItemViewType(position: Int): Int {
        return manager.getItemViewType(items, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return manager.onCreateViewHolder(parent, viewType)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        manager.bindViewHolder(items, position, holder)
    }

    fun setItemList(items: List<T>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}