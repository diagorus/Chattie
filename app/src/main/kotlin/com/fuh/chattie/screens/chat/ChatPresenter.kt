package com.fuh.chattie.screens.chat

import com.fuh.chattie.model.Message
import com.fuh.chattie.model.datastore.ChatRoomsDataStore
import com.fuh.chattie.model.datastore.CurrentUserIdDataStore
import com.fuh.chattie.model.datastore.MessagesDataStore
import java.util.*

/**
 * Created by lll on 18.08.2017.
 */
class ChatPresenter(
        private val view: ChatContract.View,
        currentUserIdDataStore: CurrentUserIdDataStore,
        private val messagesDataStore: MessagesDataStore,
        private val parameters: Parameters
) : ChatContract.Presenter {

    private val userId = currentUserIdDataStore.getCurrentUserId()

    override fun start() {
        loadChat()
    }

    override fun loadChat() {
        val query = messagesDataStore.getAllMessagesQuery(parameters.chatRoomId)

        view.showChat(userId, query)
    }

    override fun pushMessage(messageText: String) {
        val message = Message(userId, messageText, Date().time)

        messagesDataStore.postMessage(parameters.chatRoomId, message)
    }

    data class Parameters(val chatRoomId: String)
}