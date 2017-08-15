package com.fuh.chattie.screens.chat

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fuh.chattie.R

/**
 * Created by lll on 15.08.2017.
 */
enum class ChatMessageViewHolderType(
        @LayoutRes val layoutId: Int
) {
    OUTGOING(R.layout.chat_item_outgoing),
    INCOMING(R.layout.chat_item_incoming);

    companion object {
        fun getByLayoutId(layoutId: Int): ChatMessageViewHolderType? =
                ChatMessageViewHolderType.values().find { it.layoutId == layoutId }
    }

    fun createViewHolder(parent: ViewGroup): BaseChatViewHolder = when(this) {
        ChatMessageViewHolderType.OUTGOING -> ChatMessageOutgoingViewHolder(create(parent))
        ChatMessageViewHolderType.INCOMING -> ChatMessageIncomingViewHolder(create(parent))
    }

    private fun create(parent: ViewGroup): View =
            LayoutInflater.from(parent.context).inflate(layoutId, parent)
}