package com.fuh.chattie.screens.chat

import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.fuh.chattie.screens.model.ChatMessage
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.Query

/**
 * Created by lll on 15.08.2017.
 */
class ChatAdapter(
        val user: FirebaseUser,
        query: Query
) : FirebaseRecyclerAdapter<ChatMessage, BaseChatViewHolder>(
        ChatMessage::class.java,
        0,
        BaseChatViewHolder::class.java,
        query
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseChatViewHolder {
        val type = ChatMessageViewHolderType.getByLayoutId(viewType)!!

        return type.createViewHolder(parent)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)

        return decideViewType(item)
    }

    override fun populateViewHolder(viewHolder: BaseChatViewHolder, model: ChatMessage, position: Int) {
        viewHolder.bind(model)
    }

    private fun decideViewType(item: ChatMessage): Int {
        return if(user.uid == item.uid) {
            ChatMessageViewHolderType.OUTGOING.layoutId
        } else {
            ChatMessageViewHolderType.INCOMING.layoutId
        }
    }
}