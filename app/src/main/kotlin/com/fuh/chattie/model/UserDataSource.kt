package com.fuh.chattie.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by lll on 18.08.2017.
 */
class UserDataSource {
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun getUser(): Single<User> {
        return Single.create { emitter ->
            firebaseAuth.currentUser?.let { user ->
                emitter.onSuccess(User(user.displayName, user.photoUrl))
            } ?: emitter.onError(IllegalStateException("Current user is not set"))
        }
    }

    fun updateUser(user: User): Completable {
        val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(user.name)
                .setPhotoUri(user.photoUri)
                .build()

        return Completable.create { emitter ->
            firebaseAuth.currentUser?.let { user ->
                user.updateProfile(profileUpdates)
                        .addOnFailureListener { emitter.onError(it) }
                        .addOnCompleteListener { emitter.onComplete() }
            } ?: emitter.onError(IllegalStateException("Current user is not set"))
        }
    }
}