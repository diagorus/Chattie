package com.fuh.chattie.screens.chat

import com.fuh.chattie.model.ChatMessage
import com.fuh.chattie.model.User
import com.fuh.chattie.model.currentuser.CurrentUserDataStore
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

/**
 * Created by lll on 18.08.2017.
 */
class ChatPresenter(
        private val view: ChatContract.View,
        private val currentUserDataStore: CurrentUserDataStore
) : ChatContract.Presenter {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    override fun start() {
        loadChat()
    }

    override fun loadChat() {
        val currentUser = currentUserDataStore.getUser().toObservable()
        val query = Observable.just(firebaseDatabase.reference)
        Observable.zip<User, Query, Unit>(
                currentUser,
                query,
                BiFunction { t1, t2 ->
                    view.showChat(t1, t2)
                }
        )
                .subscribe()
    }

    override fun pushMessage(message: ChatMessage) {
        firebaseDatabase
                .reference
                .push()
                .setValue(message)
    }
}