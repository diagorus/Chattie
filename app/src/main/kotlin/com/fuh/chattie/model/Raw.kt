package com.fuh.chattie.model

/**
 * Created by lll on 22.08.2017.
 */
data class MessageRaw(
        var userId: String? = null,
        var text: String? = null,
        var timestamp: Long = 0
)

data class UserRaw(
        var name: String? = null,
        var photoUrl: String? = null
)

data class ChatRoomRaw(
        var title: String? = null,
        var lastMessage: MessageRaw? = null,
        var members: Map<String, Boolean>? = null
)