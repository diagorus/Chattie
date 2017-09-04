package com.fuh.chattie.screens.chatrooms

import android.view.LayoutInflater
import android.view.ViewGroup
import com.fuh.chattie.R
import com.fuh.chattie.model.ChatRoom
import com.fuh.chattie.utils.ui.BaseAdapter

/**
 * Created by lll on 04.09.2017.
 */
class ChatRoomsAdapter : BaseAdapter<ChatRoom, ChatRoomViewHolderPres>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomViewHolderPres {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.chatroom_item, parent, false)

        return ChatRoomViewHolderPres(itemView)
    }
}