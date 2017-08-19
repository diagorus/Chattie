package com.fuh.chattie.screens.chat

import com.fuh.chattie.model.ChatMessage
import com.fuh.chattie.model.User
import com.fuh.chattie.util.BasePresenter
import com.fuh.chattie.util.BaseView
import com.google.firebase.database.Query

/**
 * Created by lll on 18.08.2017.
 */
object ChatContract {
    interface View : BaseView<Presenter> {
        fun showChat(currentUser: User, query: Query)
    }

    interface Presenter : BasePresenter {
        fun loadChat()
        fun pushMessage(message: ChatMessage)
    }
}
