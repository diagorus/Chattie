package com.fuh.chattie.screens.chat

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.fuh.chattie.model.Message

/**
 * Created by lll on 31.08.2017.
 */
class ChatAdapter(
        private val currentUserId: String,
        initialMessages: List<Message> = listOf()
) : RecyclerView.Adapter<BaseChatViewHolder>() {

    private val items: MutableList<Message> = initialMessages.toMutableList()

    fun addMessage(message: Message) {
        items.add(message)
        notifyItemInserted(items.lastIndex)
    }

    fun addAllMessages(messages: List<Message>) {
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

    private fun decideViewType(item: Message): Int {
        return if (currentUserId == item.user?.id) {
            ChatMessageViewHolderType.OUTGOING.layoutId
        } else {
            ChatMessageViewHolderType.INCOMING.layoutId
        }
    }
}