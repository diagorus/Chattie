package com.fuh.chattie.model

/**
 * Created by lll on 22.08.2017.
 */
data class MessageRaw(
        var userId: String? = null,
        var text: String? = null,
        var time: Long = 0
)

data class UserRaw(
        var name: String? = null,
        var photoUrl: String? = null
)