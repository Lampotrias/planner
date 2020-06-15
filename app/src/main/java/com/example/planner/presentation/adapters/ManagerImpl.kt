package com.example.planner.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ManagerImpl<T> : ManagerAdapt {

    private val delegates = mutableMapOf<Int, DelegateAdapter<T>>()

    fun addDelegate(delegateAdapter: DelegateAdapter<T>) {
        delegates[delegates.size] = delegateAdapter
    }

    fun getItemViewType(items: List<T>, position: Int): Int {
        for ((key, delegate) in delegates) {
            if (delegate.isForViewType(items, position)) {
                return key
            }
        }
        throw NullPointerException("not get item view type")
    }

    fun bindViewHolder(
        items: List<T>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        isSelected: Boolean = false
    ) {

        val delegate = delegates[holder.itemViewType]
            ?: throw NullPointerException("not view holder for viewType")
        delegate.onBindViewHolder(items, position, holder, isSelected)
    }

    fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val delegate =
            delegates[viewType] ?: throw NullPointerException("not view holder for viewType")
        return delegate.onCreateViewHolder(viewGroup)
    }
}