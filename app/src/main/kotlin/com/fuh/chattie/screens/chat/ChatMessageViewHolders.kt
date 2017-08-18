package com.fuh.chattie.screens.chat

import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.View
import com.fuh.chattie.model.ChatMessage
import kotlinx.android.synthetic.main.chat_item_incoming.view.*
import kotlinx.android.synthetic.main.chat_item_outgoing.view.*

/**
 * Created by lll on 15.08.2017.
 */
abstract class BaseChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(message: ChatMessage)
}

class ChatMessageOutgoingViewHolder(itemView: View) : BaseChatViewHolder(itemView) {

    override fun bind(message: ChatMessage) =
            with(itemView) {
                tvChatItemOutgoingMessage.text = message.text
                tvChatItemOutgoingTime.text = DateFormat.format("HH:mm", message.time)
            }
}

class ChatMessageIncomingViewHolder(itemView: View) : BaseChatViewHolder(itemView) {

    override fun bind(message: ChatMessage) =
            with(itemView) {
                tvChatItemIncomingName.text = message.user
                tvChatItemIncomingMessage.text = message.text
                tvChatItemIncomingTime.text = DateFormat.format("HH:mm", message.time)
            }
}