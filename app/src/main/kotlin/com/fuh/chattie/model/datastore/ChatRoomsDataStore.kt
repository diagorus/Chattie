package com.fuh.chattie.model.datastore

import com.fuh.chattie.model.ChatRoom
import com.fuh.chattie.util.extentions.observeCompletion
import io.reactivex.Completable
import com.fuh.chattie.model.DATABASE_CHAT_ROOMS
import com.google.firebase.database.*
import io.reactivex.Observable


/**
 * Created by lll on 22.08.2017.
 */
class ChatRoomsDataStore(private val firebaseDatabase: FirebaseDatabase) {

    fun postChatRoom(userId: String, chatRoom: ChatRoom): Completable {
        return firebaseDatabase
                .reference
                .child(DATABASE_CHAT_ROOMS)
                .child(userId)
                .push()
                .setValue(chatRoom)
                .observeCompletion()
    }

    fun getAllChatRooms(userId: String): Observable<ChatRoom> {
        return Observable.create { emitter ->
            firebaseDatabase
                    .reference
                    .child(DATABASE_CHAT_ROOMS)
                    .child(userId)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val dataSnapshots = dataSnapshot.children.iterator()

                            while (dataSnapshots.hasNext()) {
                                val dataSnapshotChild = dataSnapshots.next()

                                val chatRoom = dataSnapshotChild.getValue<ChatRoom>(ChatRoom::class.java)

                                chatRoom?.let {
                                    emitter.onNext(chatRoom)
                                } ?: emitter.onError(IllegalStateException("No data found, chatRoom is null"))
                            }
                            emitter.onComplete()
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            emitter.onError(databaseError.toException())
                        }
                    })
        }
    }

    fun getAllChatRoomsQuery(userId: String): Query {
        return firebaseDatabase
                .reference
                .child(DATABASE_CHAT_ROOMS)
                .child(userId)
    }
}