package com.fuh.chattie.util.extentions

import android.net.Uri
import com.fuh.chattie.model.storage.ImageDataStore
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by lll on 19.08.2017.
 */
fun UploadTask.observeProgress(): Observable<Progress> {
    return Observable.create { emitter ->
        this
                .addOnProgressListener {
                    val progressPercents = ((100.0 * it.bytesTransferred) / it.totalByteCount).toInt()
                    emitter.onNext(Progress(progressPercents))
                }
                .addOnFailureListener { emitter.onError(it) }
                .addOnSuccessListener { emitter.onComplete() }
    }
}

fun UploadTask.observeCompletion(): Single<Uri> {
    return Single.create { emitter ->
        this
                .addOnFailureListener { emitter.onError(it) }
                .addOnSuccessListener { emitter.onSuccess(it.downloadUrl!!) }
    }
}

data class Progress(val percents: Int)