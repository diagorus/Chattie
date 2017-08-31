package com.fuh.chattie.screens.chat

import com.fuh.chattie.model.*
import com.fuh.chattie.model.datastore.ChatRoomsDataStore
import com.fuh.chattie.model.datastore.CurrentUserIdDataStore
import com.fuh.chattie.model.datastore.MessagesDataStore
import com.fuh.chattie.model.datastore.UsersDataStore
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import timber.log.Timber
import java.util.*

/**
 * Created by lll on 18.08.2017.
 */
class ChatPresenter(
        private val view: ChatContract.View,
        currentUserIdDataStore: CurrentUserIdDataStore,
        private val messagesDataStore: MessagesDataStore,
        private val usersDataStore: UsersDataStore,
        private val chatRoomsDataStore: ChatRoomsDataStore,
        private val parameters: Parameters
) : ChatContract.Presenter {

    private val userId = currentUserIdDataStore.getCurrentUserId()

    override fun start() {
        loadChat()
    }

    override fun loadChat() {
        val membersMapObs = chatRoomsDataStore.getAllMemberIds(userId, parameters.chatRoomId)
                .flatMap { userId ->
                    usersDataStore.getUser(userId)
                            .map { user -> Pair(userId, user) }
                            .toObservable()
                }
                .toMap({ (userId, _) -> userId }, { (_, user) -> user })
                .toObservable()

        val rawMessagesObs = messagesDataStore.getAllMessagesContinuously(parameters.chatRoomId)

        val messagesObs = Observable.combineLatest(
                rawMessagesObs.delay { membersMapObs },
                membersMapObs,
                BiFunction { (userId, text, time): MessageRaw, membersMap: Map<String, UserRaw> ->
                    val user = membersMap[userId]

                    MessagePres(User(userId, user?.name, user?.photoUrl), text, time)
                }
        )
                .publish()
                .autoConnect()

        val initialMessagesObs = messagesObs
                .toList()

        initialMessagesObs
                .subscribe({
                    Timber.d(it.toString())

                    view.showChatInitial(userId, it)
                }, {
                    Timber.e(it, "Error loading initial messages")
                })

        messagesObs
                .skipUntil(initialMessagesObs.toObservable())
                .subscribe({
                    Timber.d(it.toString())

                    view.showChatNewMessage(it)
                }, {
                    Timber.e(it, "Error loading new message")
                })

//        val query = messagesDataStore.getAllMessagesQuery(parameters.chatRoomId)
//        view.showChat(userId, query)
    }

    override fun pushMessage(messageText: String) {
        val message = Message(userId, messageText, Date().time)

        messagesDataStore.postMessage(parameters.chatRoomId, message)
    }

    data class Parameters(val chatRoomId: String)
}