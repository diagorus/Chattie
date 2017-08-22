package com.fuh.chattie.model

/**
 * Created by lll on 10.08.2017.
 */
data class Message(
        var userId: String? = null,
        var text: String? = null,
        var time: Long = 0
)