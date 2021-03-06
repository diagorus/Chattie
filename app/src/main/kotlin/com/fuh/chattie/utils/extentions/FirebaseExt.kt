package com.fuh.chattie.utils.extentions

import android.net.Uri
import com.fuh.chattie.utils.BaseChildEventListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.google.firebase.storage.UploadTask
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by lll on 19.08.2017.
 */
fun UploadTask.observeProgress(): Observable<Progress> {
    return Observable.create { emitter ->
        addOnProgressListener {
            val progressPercents = ((100.0 * it.bytesTransferred) / it.totalByteCount).toInt()
            emitter.onNext(Progress(progressPercents))
        }
        addOnFailureListener { emitter.onError(it) }
        addOnSuccessListener { emitter.onComplete() }
    }
}

fun UploadTask.observeCompletion(): Single<Uri> {
    return Single.create { emitter ->
        addOnFailureListener { emitter.onError(it) }
        addOnSuccessListener { emitter.onSuccess(it.downloadUrl!!) }
    }
}

fun <TResult> Task<TResult>.observeCompletion(): Completable {
    return Completable.create { emitter ->
        addOnFailureListener { emitter.onError(it) }
        addOnSuccessListener { emitter.onComplete() }
    }
}

inline fun <reified T> Query.observeInitial(): Single<IndexedValue<T>> {
    return Single.create { emitter ->
        addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = dataSnapshot.getValue(T::class.java)
                val dataKey = dataSnapshot.key

                val indexedData = IndexedValue(dataKey, data)
                data?.let {
                    emitter.onSuccess(indexedData)
                } ?: emitter.onError(IllegalStateException("No data found"))
            }

            override fun onCancelled(databaseError: DatabaseError) {
                emitter.onError(databaseError.toException())
            }
        })
    }
}

inline fun <reified T> Query.observeAllValuesOnce(): Observable<IndexedValue<T>> {
    return Observable.create { emitter ->
        this
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val dataSnapshots = dataSnapshot.children.iterator()

                        while (dataSnapshots.hasNext()) {
                            val dataSnapshotChild = dataSnapshots.next()

                            val child = dataSnapshotChild.getValue(T::class.java)
                            val childKey = dataSnapshotChild.key

                            val indexedChild = IndexedValue(childKey, child)
                            child?.let {
                                emitter.onNext(indexedChild)
                            } ?: emitter.onError(IllegalStateException("No data found, ${T::class.java.simpleName} is null"))
                        }

                        emitter.onComplete()
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        emitter.onError(databaseError.toException())
                    }
                })
    }
}

fun Query.observeAllKeysOnce(): Observable<String> {
    return Observable.create { emitter ->
        addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dataSnapshots = dataSnapshot.children.iterator()

                while (dataSnapshots.hasNext()) {
                    val dataSnapshotChild = dataSnapshots.next()
                    val childKey = dataSnapshotChild.key

                    childKey?.let {
                        emitter.onNext(childKey)
                    } ?: emitter.onError(IllegalStateException("Key is null"))
                }

                emitter.onComplete()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                emitter.onError(databaseError.toException())
            }
        })
    }
}

inline fun <reified T> Query.observeNewValues(): Observable<IndexedValue<T>> {
    return Observable.create<IndexedValue<T>> { emitter ->
        addChildEventListener(object : BaseChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String) {
                val newChild = dataSnapshot.getValue(T::class.java)
                val newChildKey = dataSnapshot.key
                val indexedNewChild = IndexedValue(newChildKey, newChild)

                newChild?.let {
                    emitter.onNext(indexedNewChild)
                } ?: apply {
                    val exception = IllegalStateException("No data found, ${T::class.java.simpleName} is null")
                    emitter.onError(exception)
                }
            }

                override fun onCancelled(databaseError: DatabaseError) {
                    emitter.onError(databaseError.toException())
                }
        })
    }
            .publish()
            .autoConnect()
}

data class IndexedValue<out T>(val id: String? = null, val value: T? = null)

data class Progress(val percents: Int)