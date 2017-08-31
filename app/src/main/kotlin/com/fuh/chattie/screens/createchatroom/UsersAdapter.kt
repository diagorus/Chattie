package com.fuh.chattie.screens.createchatroom

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.*
import com.fuh.chattie.R
import com.fuh.chattie.model.User
import com.fuh.chattie.utils.SelectableAdapter
import com.fuh.chattie.utils.extentions.loadImageByUri
import kotlinx.android.synthetic.main.user_item.view.*

/**
 * Created by lll on 23.08.2017.
 */
class UsersAdapter(
        items: List<User>,
        onSelectionSuccess: (List<Int>) -> Unit
) : SelectableAdapter<UsersAdapter.ViewHolder>(onSelectionSuccess) {

    var items: List<User> = items
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UsersAdapter.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        val data = items[position]

        holder.bind(data)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: User) {
            with(itemView) {
                tvUserItemName.text = data.name

                data.photoUrl?.let {
                    val photoUri = Uri.parse(it)
                    ivUserItemPhoto.loadImageByUri(photoUri)
                } ?: ivUserItemPhoto.loadImageByUri(null)
            }
        }
    }
}