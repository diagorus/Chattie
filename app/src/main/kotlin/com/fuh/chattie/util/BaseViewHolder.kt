package com.fuh.chattie.util

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by lll on 23.08.2017.
 */
abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: T, onClick: (T) -> Unit)
}