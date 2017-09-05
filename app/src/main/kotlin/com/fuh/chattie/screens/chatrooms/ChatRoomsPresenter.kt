package com.fuh.chattie.screens.chatrooms

import com.fuh.chattie.model.*
import com.fuh.chattie.model.datastore.ChatRoomsDataStore
import com.fuh.chattie.model.datastore.CurrentUserIdDataStore
import com.fuh.chattie.model.datastore.UsersDataStore
import com.fuh.chattie.utils.extentions.IndexedValue
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import timber.log.Timber

/**
 * Created by lll on 23.08.2017.
 */
class ChatRoomsPresenter(
        private val view: ChatRoomsContract.View,
        currentUserIdDataStore: CurrentUserIdDataStore,
        private val usersDataStore: UsersDataStore,
        private val chatRoomsDataStore: ChatRoomsDataStore
) : ChatRoomsContract.Presenter {

    private val currentUserId = currentUserIdDataStore.getCurrentUserId()

    override fun start() {
        loadChatRooms()
    }

    override fun loadChatRooms() {
//        val userId = currentUserIdDataStore.getCurrentUserId()
//        val chatRoomsQuery = chatRoomsDataStore.getAllChatRoomsQuery(userId)
//
//        view.showChatRooms(chatRoomsQuery)

        val chatRoomsObs = chatRoomsDataStore.getAllChatRooms(currentUserId)
        val chatRoomsLastMessageUsersObs = chatRoomsObs.flatMap {
            usersDataStore.getUser(it.value?.lastMessage?.userId!!).toObservable()
        }

        Observable.zip(
                chatRoomsObs,
                chatRoomsLastMessageUsersObs,
                BiFunction { (chatRoomId, chatRoomRaw): IndexedValue<ChatRoomRaw>, (userId, userRaw): IndexedValue<UserRaw> ->
                    ChatRoom(
                            chatRoomId,
                            chatRoomRaw?.title,
                            Message(
                                    User(userId, userRaw?.name, userRaw?.photoUrl),
                                    chatRoomRaw?.lastMessage?.text,
                                    chatRoomRaw?.lastMessage?.timestamp
                            ),
                            chatRoomRaw?.members
                    )
                }
        )
                .toList()
                .subscribe({
                    view.showChatRoomsInitial(it)
                }, {
                    Timber.e(it)
                })
    }
}