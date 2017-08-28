package com.fuh.chattie.model.datastore

import com.fuh.chattie.model.DATABASE_CHAT_ROOMS
import com.fuh.chattie.model.DATABASE_MESSAGES
import com.fuh.chattie.model.Message
import com.fuh.chattie.util.extentions.toMap
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

/**
 * Created by lll on 22.08.2017.
 */
class MessagesDataStore(private val firebaseDatabase: FirebaseDatabase) {

    fun postMessage(chatRoomId: String, message: Message) {
        val messageId = firebaseDatabase
                .reference
                .child(DATABASE_MESSAGES)
                .child(chatRoomId)
                .push()
                .key

        val messageValues = message.toMap()

        val childUpdates = mapOf(
                "${DATABASE_MESSAGES}/$chatRoomId/$messageId" to messageValues,
                "${DATABASE_CHAT_ROOMS}/${message.userId}/$chatRoomId/lastMessage" to messageValues
        )

        firebaseDatabase.reference.updateChildren(childUpdates)
    }

    fun getAllMessagesQuery(chatRoomId: String): Query {
        return firebaseDatabase
                .reference
                .child(DATABASE_MESSAGES)
                .child(chatRoomId)
    }
}