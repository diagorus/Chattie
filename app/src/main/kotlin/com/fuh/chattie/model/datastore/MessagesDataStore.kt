package com.fuh.chattie.model.datastore

import com.fuh.chattie.model.Message
import com.fuh.chattie.model.MessageRaw
import com.fuh.chattie.model.datastore.contracts.DATABASE_CHAT_ROOMS
import com.fuh.chattie.model.datastore.contracts.DATABASE_MESSAGES
import com.fuh.chattie.utils.extentions.observeAllValuesOnce
import com.fuh.chattie.utils.extentions.observeNewValues
import com.fuh.chattie.utils.extentions.toMap
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import io.reactivex.Observable

/**
 * Created by lll on 22.08.2017.
 */
class MessagesDataStore(private val firebaseDatabase: FirebaseDatabase) {

    fun postMessage(chatRoomId: String, message: MessageRaw) {
        val messageId = firebaseDatabase
                .reference
                .child(DATABASE_MESSAGES)
                .child(chatRoomId)
                .push()
                .key

        val messageValues = message.toMap()

        val childUpdates = mapOf(
                "$DATABASE_MESSAGES/$chatRoomId/$messageId" to messageValues,
                "$DATABASE_CHAT_ROOMS/${message.userId}/$chatRoomId/lastMessage" to messageValues
        )

        firebaseDatabase.reference.updateChildren(childUpdates)
    }

    fun getAllMessagesQuery(chatRoomId: String): Query {
        return firebaseDatabase
                .reference
                .child(DATABASE_MESSAGES)
                .child(chatRoomId)
    }

    fun listenForNewMessages(chatRoomId: String): Observable<MessageRaw> {
        return firebaseDatabase
                .reference
                .child(DATABASE_MESSAGES)
                .child(chatRoomId)
                .observeNewValues()
    }

    fun getAllMessagesContinuously(chatRoomId: String): Observable<MessageRaw> {
        return firebaseDatabase
                .reference
                .child(DATABASE_MESSAGES)
                .child(chatRoomId)
                .observeAllValuesOnce()
    }
}