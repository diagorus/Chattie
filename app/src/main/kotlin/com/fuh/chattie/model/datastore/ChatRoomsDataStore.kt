package com.fuh.chattie.model.datastore

import com.fuh.chattie.model.ChatRoom
import com.fuh.chattie.model.ChatRoomRaw
import com.fuh.chattie.model.datastore.contracts.DATABASE_CHAT_ROOMS
import com.fuh.chattie.model.datastore.contracts.DATABASE_CHAT_ROOMS_MEMBERS
import com.fuh.chattie.utils.extentions.observeAllKeysOnce
import com.fuh.chattie.utils.extentions.observeCompletion
import com.fuh.chattie.utils.extentions.observeInitial
import com.fuh.chattie.utils.extentions.toMap
import io.reactivex.Completable
import com.google.firebase.database.*
import io.reactivex.Observable
import io.reactivex.Single


/**
 * Created by lll on 22.08.2017.
 */
class ChatRoomsDataStore(private val firebaseDatabase: FirebaseDatabase) {

    fun postChatRoom(chatRoom: ChatRoomRaw): Completable {
        val userId = chatRoom.members?.keys?.first()

        val newChatRoomId = firebaseDatabase
                .reference
                .child(DATABASE_CHAT_ROOMS)
                .child(userId)
                .push()
                .key

        val chatRoomValues = chatRoom.toMap()

        val childUpdates = chatRoom.members
                ?.map { "${it.key}/$newChatRoomId" to chatRoomValues }
                ?.toMap()

        return firebaseDatabase
                .reference
                .child(DATABASE_CHAT_ROOMS)
                .updateChildren(childUpdates)
                .observeCompletion()
    }

    fun getChatRoom(userId: String, chatRoomId: String): Single<ChatRoomRaw> {
       return firebaseDatabase
                .reference
                .child(DATABASE_CHAT_ROOMS)
                .child(userId)
                .child(chatRoomId)
                .observeInitial()
    }

    fun getAllChatRoomsQuery(userId: String): Query {
        return firebaseDatabase
                .reference
                .child(DATABASE_CHAT_ROOMS)
                .child(userId)
    }

    fun getAllMemberIds(userId: String, chatRoomId: String): Observable<String> {
        return firebaseDatabase
                .reference
                .child(DATABASE_CHAT_ROOMS)
                .child(userId)
                .child(chatRoomId)
                .child(DATABASE_CHAT_ROOMS_MEMBERS)
                .observeAllKeysOnce()
    }
}