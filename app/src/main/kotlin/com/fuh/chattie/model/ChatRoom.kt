package com.fuh.chattie.model

/**
 * Created by lll on 22.08.2017.
 */
data class ChatRoom(
        var title: String?,
        var lastMessage: Message,
        var members: Map<String, Boolean>
)