package com.fuh.chattie.screens.chatrooms

import android.view.View
import com.fuh.chattie.model.ChatRoom
import com.fuh.chattie.utils.extentions.toTimeString
import com.fuh.chattie.utils.ui.BaseViewHolder
import kotlinx.android.synthetic.main.chatroom_item.view.*

/**
 * Created by lll on 04.09.2017.
 */
class ChatRoomViewHolderPres(itemView: View) : BaseViewHolder<ChatRoom>(itemView) {

    override fun bind(data: ChatRoom, onClick: (ChatRoom) -> Unit) {
        with(itemView) {
            tvChatRoomItemTitle.text = data.title
            tvChatRoomItemLastMessageDate.text = data.lastMessage?.timestamp?.toTimeString()
            tvChatRoomItemLastMessageText.text = data.lastMessage?.text
//            ivChatRoomItemPhoto.loadImageByUri()

            setOnClickListener { onClick(data) }
        }
    }
}