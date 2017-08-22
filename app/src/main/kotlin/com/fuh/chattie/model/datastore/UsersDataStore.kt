package com.fuh.chattie.model.datastore

import com.fuh.chattie.model.User
import com.fuh.chattie.util.extentions.observeCompletion
import com.fuh.chattie.util.extentions.observeInitial
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by lll on 19.08.2017.
 */
class UsersDataStore(private val firebaseDatabase: FirebaseDatabase) {

    companion object {
        private const val DATABASE_USERS = "users"
    }

    fun postUser(user: User): Completable {
        return firebaseDatabase
                .reference
                .child(DATABASE_USERS)
                .child(user.id)
                .setValue(user)
                .observeCompletion()
    }

    fun getUser(id: String): Single<User> {
        return firebaseDatabase
                .reference
                .child(DATABASE_USERS)
                .child(id)
                .observeInitial()
    }
}