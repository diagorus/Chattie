package com.fuh.chattie.screens.chat

import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.fuh.chattie.model.Message
import com.fuh.chattie.model.User
import com.google.firebase.database.Query

/**
 * Created by lll on 15.08.2017.
 */
class ChatAdapter(
        private val currentUserId: String,
        query: Query
) : FirebaseRecyclerAdapter<Message, BaseChatViewHolder>(
        Message::class.java,
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

    override fun populateViewHolder(viewHolder: BaseChatViewHolder, model: Message, position: Int) {
        viewHolder.bind(model)
    }

    private fun decideViewType(item: Message): Int {
        return if(currentUserId == item.userId) {
            ChatMessageViewHolderType.OUTGOING.layoutId
        } else {
            ChatMessageViewHolderType.INCOMING.layoutId
        }
    }
}