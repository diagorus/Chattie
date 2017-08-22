package com.fuh.chattie.util.extentions

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.UploadTask
import io.reactivex.Completable
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

fun <TResult> Task<TResult>.observeCompletion(): Completable {
    return Completable.create { emitter ->
        this
                .addOnFailureListener { emitter.onError(it) }
                .addOnSuccessListener { emitter.onComplete() }
    }
}

inline fun <reified T> DatabaseReference.observeInitial(): Single<T> {
    return Single.create { emitter ->
        this
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val data = dataSnapshot.getValue(T::class.java)
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

data class Progress(val percents: Int)