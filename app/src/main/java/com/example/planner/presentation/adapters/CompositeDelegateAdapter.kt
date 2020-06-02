package com.example.planner.presentation.adapters

import android.util.SparseArray
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class CompositeDelegateAdapter<T> private constructor(
    private val typeToAdapterMap: SparseArray<DelegateAdapter<RecyclerView.ViewHolder, T>>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data: MutableList<T> = ArrayList()
    override fun getItemViewType(position: Int): Int {
        for (i in FIRST_VIEW_TYPE until typeToAdapterMap.size()) {
            val delegate: DelegateAdapter<RecyclerView.ViewHolder, T> = typeToAdapterMap.valueAt(i)
            if (delegate.isForViewType(data, position)) {
                return typeToAdapterMap.keyAt(i)
            }
        }
        throw NullPointerException(
            "Can not get viewType for position $position"
        )
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): RecyclerView.ViewHolder {
        return typeToAdapterMap[viewType]
            .onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int
    ) {
        val delegateAdapter: DelegateAdapter<RecyclerView.ViewHolder, T> = typeToAdapterMap[getItemViewType(position)]
        delegateAdapter.onBindViewHolder(holder, data, position)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        typeToAdapterMap[holder.itemViewType]
            .onRecycled(holder)
    }

    fun swapData(data: List<T>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class Builder<T> {
        private var count = 0
        private val typeToAdapterMap: SparseArray<DelegateAdapter<RecyclerView.ViewHolder, T>> =
            SparseArray<DelegateAdapter<RecyclerView.ViewHolder, T>>()

        fun add(
            delegateAdapter: DelegateAdapter<RecyclerView.ViewHolder, T>
        ): Builder<T> {
            typeToAdapterMap.put(count++, delegateAdapter)
            return this
        }

        fun build(): CompositeDelegateAdapter<T> {
            require(count != 0) { "Register at least one adapter" }
            return CompositeDelegateAdapter(typeToAdapterMap)
        }

    }

    companion object {
        private const val FIRST_VIEW_TYPE = 0
    }

}