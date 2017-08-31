package com.fuh.chattie.model.datastore

import android.net.Uri
import com.fuh.chattie.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by lll on 18.08.2017.
 */
class CurrentUserAuthDataStore(private val firebaseAuth: FirebaseAuth) {

    fun getUser(): Single<User> {
        return Single.create { emitter ->
            firebaseAuth.currentUser?.let { user ->
                emitter.onSuccess(User(user.uid, user.displayName, user.photoUrl.toString()))
            } ?: emitter.onError(IllegalStateException("Current user is not set"))
        }
    }

    fun updateUser(user: User): Completable {
        val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(user.name)
                .setPhotoUri(Uri.parse(user.photoUrl))
                .build()

        return Completable.create { emitter ->
            firebaseAuth.currentUser?.let { user ->
                user.updateProfile(profileUpdates)
                        .addOnFailureListener { emitter.onError(it) }
                        .addOnSuccessListener { emitter.onComplete() }
            } ?: emitter.onError(IllegalStateException("Current user is not set"))
        }
    }
}