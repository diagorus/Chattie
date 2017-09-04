package com.fuh.chattie.screens.chat

import com.fuh.chattie.model.*
import com.fuh.chattie.model.datastore.ChatRoomsDataStore
import com.fuh.chattie.model.datastore.CurrentUserIdDataStore
import com.fuh.chattie.model.datastore.MessagesDataStore
import com.fuh.chattie.model.datastore.UsersDataStore
import com.fuh.chattie.utils.extentions.IndexedValue
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
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

    private val currentUserId = currentUserIdDataStore.getCurrentUserId()

    private val compositeDisposable = CompositeDisposable()

    override fun start() {
        loadChat()
    }

    override fun loadChat() {
        val membersMapObs = chatRoomsDataStore.getChatRoom(currentUserId, parameters.chatRoomId)
                .map { it.value }
                .flatMapObservable { Observable.fromIterable(it.members?.keys) }
                .flatMap { usersDataStore.getUser(it).toObservable() }
                .toMap({ (id, _) -> id!! }, { (_, userRaw) -> userRaw!! })
                .toObservable()
        val rawMessagesObs = messagesDataStore.getAllMessages(parameters.chatRoomId)

        val initialMessagesDisposable = Observable.combineLatest(
                rawMessagesObs.delay { membersMapObs },
                membersMapObs,
                BiFunction { (id, messageRaw): IndexedValue<MessageRaw>, membersMap: Map<String, UserRaw> ->
                    val user = membersMap[messageRaw?.userId]

                    Message(id, User(messageRaw?.userId, user?.name, user?.photoUrl), messageRaw?.text, messageRaw?.timestamp)
                }
        )
                .toList()
                .subscribe({
                    Timber.d(it.toString())

                    view.showChatInitial(currentUserId, it)
                }, {
                    Timber.e(it, "Error loading initial messages")
                })
        compositeDisposable.add(initialMessagesDisposable)

        val newMessagesDisposable = messagesDataStore.listenForNewMessages(parameters.chatRoomId)
                .withLatestFrom(
                        membersMapObs,
                        BiFunction { (id, messageRaw): IndexedValue<MessageRaw>, membersMap: Map<String, UserRaw> ->
                            val userRaw = membersMap[messageRaw?.userId]

                            Message(id, User(messageRaw?.userId, userRaw?.name, userRaw?.photoUrl), messageRaw?.text, messageRaw?.timestamp)
                        }
                )
                .subscribe({
                    view.showChatNewMessage(it)
                }, {
                    Timber.e(it)
                })
        compositeDisposable.add(newMessagesDisposable)
    }

    override fun pushMessage(messageText: String) {
        val message = MessageRaw(currentUserId, messageText, Date().time)

        val chatRoomDisposable = chatRoomsDataStore.getChatRoom(currentUserId, parameters.chatRoomId)
                .subscribe({
                    messagesDataStore.postMessage(parameters.chatRoomId, it.value!!, message)
                }, {
                    Timber.e(it)
                })
        compositeDisposable.add(chatRoomDisposable)
    }

    override fun stop() {
        compositeDisposable.dispose()
    }

    data class Parameters(val chatRoomId: String)
}