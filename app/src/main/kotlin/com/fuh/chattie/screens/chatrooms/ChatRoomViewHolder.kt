package com.fuh.chattie.screens.chatrooms

import android.view.View
import com.fuh.chattie.model.ChatRoom
import com.fuh.chattie.utils.BaseViewHolder
import kotlinx.android.synthetic.main.chatroom_item.view.*

/**
 * Created by lll on 23.08.2017.
 */
class ChatRoomViewHolder(itemView: View) : BaseViewHolder<ChatRoom>(itemView) {

    override fun bind(data: ChatRoom, onClick: (ChatRoom) -> Unit) {
        with(itemView) {
            tvChatRoomItemTitle.text = data.title
            tvChatRoomItemLastMessage.text = data.lastMessage?.text

            setOnClickListener { onClick(data) }
        }
    }
}