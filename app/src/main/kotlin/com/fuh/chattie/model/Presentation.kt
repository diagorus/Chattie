package com.fuh.chattie.model

/**
 * Created by lll on 23.08.2017.
 */
data class Message(
        var id: String? = null,
        var user: User? = null,
        var text: String? = null,
        var timestamp: Long? = 0
)

data class User(
        var id: String? = null,
        var name: String? = null,
        var photoUrl: String? = null
)

data class ChatRoom(
        var id: String? = null,
        var title: String? = null,
        var lastMessage: Message? = null,
        var members: Map<String, Boolean>? = null
)