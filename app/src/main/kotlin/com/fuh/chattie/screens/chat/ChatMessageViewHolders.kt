package com.fuh.chattie.screens.chat

import android.support.v7.widget.RecyclerView
import android.view.View
import com.fuh.chattie.model.Message
import com.fuh.chattie.utils.extentions.getNameOrDefault
import com.fuh.chattie.utils.extentions.getPhotoUriOrDefault
import com.fuh.chattie.utils.extentions.loadImageByUri
import com.fuh.chattie.utils.extentions.toTimeString
import kotlinx.android.synthetic.main.chat_item_incoming.view.*
import kotlinx.android.synthetic.main.chat_item_outgoing.view.*

/**
 * Created by lll on 15.08.2017.
 */
abstract class BaseChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(message: Message)
}

class ChatMessageOutgoingViewHolder(itemView: View) : BaseChatViewHolder(itemView) {

    override fun bind(message: Message) =
            with(itemView) {
                tvChatItemOutgoingMessage.text = message.text
                tvChatItemOutgoingTime.text = message.timestamp?.toTimeString()
            }
}

class ChatMessageIncomingViewHolder(itemView: View) : BaseChatViewHolder(itemView) {

    override fun bind(message: Message) =
            with(itemView) {
                val userName = message.user.getNameOrDefault(context)
                tvChatItemIncomingName.text = userName

                val photoUri = message.user.getPhotoUriOrDefault(context)
                ivChatItemIncomingPhoto.loadImageByUri(photoUri)

                tvChatItemIncomingMessage.text = message.text
                tvChatItemIncomingTime.text = message.timestamp?.toTimeString()
            }
}