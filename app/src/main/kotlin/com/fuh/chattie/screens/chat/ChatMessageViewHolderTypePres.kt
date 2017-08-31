package com.fuh.chattie.screens.chat

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fuh.chattie.R

/**
 * Created by lll on 15.08.2017.
 */
enum class ChatMessageViewHolderTypePres(
        @LayoutRes val layoutId: Int
) {
    OUTGOING(R.layout.chat_item_outgoing),
    INCOMING(R.layout.chat_item_incoming);

    companion object {
        fun getByLayoutId(layoutId: Int): ChatMessageViewHolderTypePres? =
                ChatMessageViewHolderTypePres.values().find { it.layoutId == layoutId }
    }

    fun createViewHolder(parent: ViewGroup): BaseChatViewHolderPres = when(this) {
        ChatMessageViewHolderTypePres.OUTGOING -> ChatMessageOutgoingViewHolderPres(create(parent))
        ChatMessageViewHolderTypePres.INCOMING -> ChatMessageIncomingViewHolderPres(create(parent))
    }

    private fun create(parent: ViewGroup): View =
            LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
}