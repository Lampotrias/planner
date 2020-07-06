package com.lamp.planner.presentation.adapters

import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

class CompositeAdapter<T>(
    private val managerImpl: ManagerImpl<T>,
    private val clickCallback: ClickItemInterface<T>? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<T>()
    private var focusedItem = RecyclerView.NO_POSITION

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        recyclerView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            val gestureDetector = GestureDetector(
                recyclerView.context,
                object : GestureDetector.SimpleOnGestureListener() {
                    override fun onSingleTapUp(e: MotionEvent?): Boolean {
                        return true
                    }

                    override fun onLongPress(e: MotionEvent?) {
                        e?.let {
                            val childView: View? = recyclerView.findChildViewUnder(e.x, e.y)
                            if (childView != null) {
                                val position = recyclerView.getChildLayoutPosition(childView)
                                if (clickCallback != null) clickCallback.onLongClick(
                                    items[position],
                                    childView
                                ) else changeFocusItem(position)
                            }
                        }
                    }
                })

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                val childView: View? = rv.findChildViewUnder(e.x, e.y)
                childView?.let {
                    if (gestureDetector.onTouchEvent(e)) {
                        val position = rv.getChildLayoutPosition(it)
                        if (clickCallback != null) clickCallback.onClick(items[position], childView)
                        else changeFocusItem(position)
                        return true
                    }
                }
                return false
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })
    }

    override fun getItemViewType(position: Int): Int {
        return managerImpl.getItemViewType(items, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return managerImpl.onCreateViewHolder(parent, viewType)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        managerImpl.bindViewHolder(items, position, holder, focusedItem == position)
    }

    private fun changeFocusItem(position: Int) {
        notifyItemChanged(focusedItem)
        Timber.tag("disabled").e(focusedItem.toString())
        focusedItem = position
        notifyItemChanged(focusedItem)
        Timber.tag("selected").e(focusedItem.toString())
    }

    fun setItemList(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun setItemListWithDefault(items: List<T>, position: Int) {
        setItemList(items)
        changeFocusItem(position)
    }

    interface ClickItemInterface<T> {
        fun onClick(item: T, view: View) {}
        fun onLongClick(item: T, view: View) {}
    }
}
