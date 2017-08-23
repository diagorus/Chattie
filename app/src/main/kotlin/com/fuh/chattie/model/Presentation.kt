package com.fuh.chattie.model

/**
 * Created by lll on 23.08.2017.
 */
data class Message(
        var userId: String? = null,
        var text: String? = null,
        var time: Long = 0
)

data class User(
        var id: String? = null,
        var name: String? = null,
        var photoUrl: String? = null
)

data class ChatRoom(
        var title: String?,
        var lastMessage: Message,
        var members: Map<String, Boolean>
)