package com.fuh.chattie.screens.chat

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.View
import com.fuh.chattie.R
import com.fuh.chattie.model.ChatMessage
import kotlinx.android.synthetic.main.chat_item.view.*

/**
 * Created by lll on 10.08.2017.
 */
class ChatMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindOutgoing(chatMessage: ChatMessage) {
        with(itemView) {
            rlChatItemForm.background = ContextCompat.getDrawable(context, R.drawable.chat_bubble_outgoing)
            tvChatItemText.text = chatMessage.text
            tvChatItemUser.text = chatMessage.user
            tvChatItemDate.text = DateFormat.format("HH:mm", chatMessage.time)
        }
    }

    fun bindIncoming(chatMessage: ChatMessage) {
        with(itemView) {
            rlChatItemForm.background = ContextCompat.getDrawable(context, R.drawable.chat_bubble_incoming)
            tvChatItemText.text = chatMessage.text
            tvChatItemUser.text = chatMessage.user
            tvChatItemDate.text = DateFormat.format("HH:mm", chatMessage.time)
        }
    }
}