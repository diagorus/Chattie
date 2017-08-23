package com.fuh.chattie.screens.createchatroom

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.fuh.chattie.R
import com.fuh.chattie.model.User

/**
 * Created by lll on 23.08.2017.
 */
class UsersAdapter(items: List<User>) : RecyclerView.Adapter<UserItemViewHolder>() {

    var items: List<User> = items
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)

        return UserItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        val data = items[position]

        holder.bind(data)
    }

    override fun getItemCount(): Int = items.size
}