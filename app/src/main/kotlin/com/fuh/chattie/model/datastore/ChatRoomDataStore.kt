package com.fuh.chattie.model.datastore

import com.fuh.chattie.model.ChatRoom
import com.fuh.chattie.util.extentions.observeCompletion
import com.fuh.chattie.util.extentions.observeInitial
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Completable
import io.reactivex.Single
import com.google.firebase.auth.FirebaseAuth
import android.text.TextUtils
import io.reactivex.Observable


/**
 * Created by lll on 22.08.2017.
 */
class ChatRoomDataStore(private val firebaseDatabase: FirebaseDatabase) {

    companion object {
        private const val DATABASE_CHAT_ROOMS = "chat_rooms"
    }

    fun postChatRoom(chatRoom: ChatRoom): Completable {
        return firebaseDatabase
                .reference
                .child(DATABASE_CHAT_ROOMS)
                .push()
                .setValue(chatRoom)
                .observeCompletion()
    }

    fun getChatRoom(id: String): Single<ChatRoom> {
        return firebaseDatabase
                .reference
                .child(DATABASE_CHAT_ROOMS)
                .child(id)
                .observeInitial()
    }

    fun getAllChatRooms(): Observable<ChatRoom> {
        firebaseDatabase
                .reference
                .child(DATABASE_CHAT_ROOMS)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val dataSnapshots = dataSnapshot.children.iterator()

                        while (dataSnapshots.hasNext()) {
                            val dataSnapshotChild = dataSnapshots.next()

                            val user = dataSnapshotChild.getValue<ChatRoom>(ChatRoom::class.java)

                            if (!TextUtils.equals(user!!.uid,
                                    FirebaseAuth.getInstance().currentUser!!.uid)) {
                                users.add(user)
                            }
                        }

                        data?.let {
                            emitter.onSuccess(data)
                        } ?: emitter.onError(IllegalStateException("No data found"))
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        emitter.onError(databaseError.toException())
                    }
                })
    }
}