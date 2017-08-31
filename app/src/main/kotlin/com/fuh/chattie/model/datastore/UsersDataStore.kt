package com.fuh.chattie.model.datastore

import com.fuh.chattie.model.User
import com.fuh.chattie.model.UserRaw
import com.fuh.chattie.model.datastore.contracts.DATABASE_USERS
import com.fuh.chattie.utils.extentions.observeAllValuesOnce
import com.fuh.chattie.utils.extentions.observeCompletion
import com.fuh.chattie.utils.extentions.observeInitial
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by lll on 19.08.2017.
 */
class UsersDataStore(private val firebaseDatabase: FirebaseDatabase) {

    fun postUser(user: User): Completable {
        return firebaseDatabase
                .reference
                .child(DATABASE_USERS)
                .child(user.id)
                .setValue(user)
                .observeCompletion()
    }

    fun getUser(id: String): Single<UserRaw> {
        return firebaseDatabase
                .reference
                .child(DATABASE_USERS)
                .child(id)
                .observeInitial()
    }

    fun getAllUsers(): Observable<User> {
        return firebaseDatabase
                .reference
                .child(DATABASE_USERS)
                .observeAllValuesOnce()
    }
}