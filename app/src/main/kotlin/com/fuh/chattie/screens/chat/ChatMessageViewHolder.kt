package com.fuh.chattie.screens.chat

import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.View
import com.fuh.chattie.screens.model.ChatMessage
import kotlinx.android.synthetic.main.chat_item.view.*

/**
 * Created by lll on 10.08.2017.
 */
class ChatMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(chatMessage: ChatMessage) {
        itemView.tvChatItemText.text = chatMessage.text
        itemView.tvChatItemUser.text = chatMessage.user
        itemView.tvChatItemDate.text = DateFormat.format("HH:mm:ss", chatMessage.time)
    }
}