package com.fuh.chattie.screens.chat

import com.fuh.chattie.model.MessagePres
import com.fuh.chattie.utils.mvp.BasePresenter
import com.fuh.chattie.utils.mvp.BaseView

/**
 * Created by lll on 18.08.2017.
 */
object ChatContract {
    interface View : BaseView<Presenter> {
        fun showChatInitial(currentUserId: String, messages: List<MessagePres>)
        fun showChatNewMessage(message: MessagePres)
    }

    interface Presenter : BasePresenter {
        fun loadChat()
        fun pushMessage(messageText: String)
    }
}
