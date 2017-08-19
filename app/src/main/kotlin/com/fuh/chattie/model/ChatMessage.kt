package com.fuh.chattie.model

/**
 * Created by lll on 10.08.2017.
 */
data class ChatMessage(
        var text: String? = null,
        var user: User? = null,
        var time: Long = 0
)