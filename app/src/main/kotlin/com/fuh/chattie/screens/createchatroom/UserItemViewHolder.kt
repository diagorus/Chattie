package com.fuh.chattie.screens.createchatroom

import android.net.Uri
import android.view.View
import com.fuh.chattie.model.User
import com.fuh.chattie.util.BaseViewHolder
import com.fuh.chattie.util.extentions.loadImageByUri
import kotlinx.android.synthetic.main.user_item.view.*

/**
 * Created by lll on 23.08.2017.
 */
class UserItemViewHolder(itemView: View) : BaseViewHolder<User>(itemView) {

    override fun bind(data: User, onClick: (User) -> Unit) {
        with(itemView) {
            tvUserItemName.text = data.name

            val photoUri = Uri.parse(data.photoUrl)
            ivUserItemPhoto.loadImageByUri(photoUri)
        }
    }
}