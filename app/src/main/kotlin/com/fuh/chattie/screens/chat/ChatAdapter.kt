package com.fuh.chattie.screens.chat

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.fuh.chattie.model.MessagePres

/**
 * Created by lll on 31.08.2017.
 */
class ChatAdapter(
        private val currentUserId: String,
        initialMessages: List<MessagePres> = listOf()
): RecyclerView.Adapter<BaseChatViewHolder>() {

    private val items: MutableList<MessagePres> = initialMessages.toMutableList()

    fun addMessage(message: MessagePres) {
        items.add(message)
        notifyItemInserted(items.lastIndex)
    }

    fun addAllMessages(messages: List<MessagePres>) {
        items.addAll(messages)
        notifyItemRangeInserted(items.lastIndex, messages.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseChatViewHolder {
        val type = ChatMessageViewHolderType.getByLayoutId(viewType)!!

        return type.createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: BaseChatViewHolder, position: Int) {
        val data = items[position]

        holder.bind(data)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        val item = items[position]

        return decideViewType(item)
    }

    private fun decideViewType(item: MessagePres): Int {
        return if(currentUserId == item.user?.id) {
            ChatMessageViewHolderType.OUTGOING.layoutId
        } else {
            ChatMessageViewHolderType.INCOMING.layoutId
        }
    }
}