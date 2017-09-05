package com.fuh.chattie.utils.ui

import android.support.v7.widget.RecyclerView

/**
 * Created by lll on 04.09.2017.
 */
abstract class BaseAdapter<T, VH: BaseViewHolder<T>>(
        initialData: List<T> = listOf()
) : RecyclerView.Adapter<VH>() {


}