package com.fuh.chattie.model.storage

import android.net.Uri
import com.fuh.chattie.model.User
import com.fuh.chattie.util.extentions.observeCompletion
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Single
import java.util.*

/**
 * Created by lll on 19.08.2017.
 */
class ImageDataStore(private val firebaseStorage: FirebaseStorage) {
    fun uploadImage(user: User, uri: Uri): Single<Uri> {
        val photoName = UUID.randomUUID().toString()

        return firebaseStorage.reference
                .child("images/")
                .child(user.id!!)
                .child(photoName)
                .putFile(uri)
                .observeCompletion()
    }
}