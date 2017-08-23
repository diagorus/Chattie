package com.fuh.chattie.model.datastore

import com.fuh.chattie.model.ChatRoom
import com.fuh.chattie.util.extentions.observeCompletion
import com.fuh.chattie.util.extentions.observeInitial
import io.reactivex.Completable
import io.reactivex.Single
import com.google.firebase.auth.FirebaseAuth
import android.text.TextUtils
import com.google.firebase.database.*
import io.reactivex.Observable


/**
 * Created by lll on 22.08.2017.
 */
class ChatRoomDataStore(private val firebaseDatabase: FirebaseDatabase) {

    companion object {
        private const val DATABASE_CHAT_ROOMS = "chat_rooms"
    }

    fun postChatRoom(userId: String,chatRoom: ChatRoom): Completable {
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