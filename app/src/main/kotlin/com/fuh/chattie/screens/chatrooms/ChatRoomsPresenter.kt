package com.fuh.chattie.screens.chatrooms

import com.fuh.chattie.model.datastore.ChatRoomsDataStore
import com.fuh.chattie.model.datastore.CurrentUserIdDataStore

/**
 * Created by lll on 23.08.2017.
 */
class ChatRoomsPresenter(
        private val view: ChatRoomsContract.View,
        private val currentUserIdDataStore: CurrentUserIdDataStore,
        private val chatRoomsDataStore: ChatRoomsDataStore
) : ChatRoomsContract.Presenter {

    override fun start() {
        loadChatRooms()
    }

    override fun loadChatRooms() {
        val userId = currentUserIdDataStore.getCurrentUserId()
        val chatRoomsQuery = chatRoomsDataStore.getAllChatRoomsQuery(userId)

        view.showChatRooms(chatRoomsQuery)
    }
}