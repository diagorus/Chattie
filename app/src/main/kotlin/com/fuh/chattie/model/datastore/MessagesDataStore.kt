package com.fuh.chattie.model.datastore

import com.fuh.chattie.model.Message
import com.fuh.chattie.util.extentions.observeCompletion
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import io.reactivex.Completable

/**
 * Created by lll on 22.08.2017.
 */
class MessagesDataStore(private val firebaseDatabase: FirebaseDatabase) {

    companion object {
        private const val DATABASE_MESSAGES = "messages"
    }

    fun postMessage(chatRoomId: String, message: Message): Completable {
        return firebaseDatabase
                .reference
                .child(DATABASE_MESSAGES)
                .child(chatRoomId)
                .push()
                .setValue(message)
                .observeCompletion()
    }

    fun getAllMessagesQuery(chatRoomId: String): Query {
        return firebaseDatabase
                .reference
                .child(DATABASE_MESSAGES)
                .child(chatRoomId)
    }
}