package com.fuh.chattie.utils.ui

import android.support.v7.widget.RecyclerView

/**
 * Created by lll on 04.09.2017.
 */
abstract class BaseAdapter<T, VH: BaseViewHolder<T>>(
        initialData: List<T> = listOf()
) : RecyclerView.Adapter<VH>() {

    private val items: MutableList<T> = initialData.toMutableList()

    fun addItem(message: T) {
        items.add(message)
        notifyItemInserted(items.lastIndex)
    }

    fun addAllItems(messages: List<T>) {
        items.addAll(messages)
        notifyItemRangeInserted(items.lastIndex, messages.size)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val data = items[position]

        holder.bind(data)
    }

    override fun getItemCount(): Int = items.size
}