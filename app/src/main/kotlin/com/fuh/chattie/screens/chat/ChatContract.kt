package com.fuh.chattie.screens.chat

import com.fuh.chattie.model.Message
import com.fuh.chattie.utils.mvp.BasePresenter
import com.fuh.chattie.utils.mvp.BaseView

/**
 * Created by lll on 18.08.2017.
 */
object ChatContract {
    interface View : BaseView<Presenter> {
        fun showChatInitial(currentUserId: String, messages: List<Message>)
        fun showChatNewMessage(message: Message)
    }

    interface Presenter : BasePresenter {
        fun loadChat()
        fun pushMessage(messageText: String)
    }
}
