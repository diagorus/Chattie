package com.fuh.chattie.screens.chatrooms

import com.fuh.chattie.model.datastore.ChatRoomDataStore
import com.fuh.chattie.model.datastore.CurrentUserIdDataStore

/**
 * Created by lll on 23.08.2017.
 */
class ChatRoomsPresenter(
        private val view: ChatRoomsCotract.View,
        private val currentUserIdDataStore: CurrentUserIdDataStore,
        private val chatRoomDataStore: ChatRoomDataStore
) : ChatRoomsCotract.Presenter {

    override fun start() {
        loadChatRooms()
    }

    override fun loadChatRooms() {
        val userId = currentUserIdDataStore.getCurrentUserId()
        val chatRoomsQuery = chatRoomDataStore.getAllChatRoomsQuery(userId)

        view.showChatRooms(chatRoomsQuery)
    }
}