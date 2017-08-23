package com.fuh.chattie.screens.chatrooms

import com.fuh.chattie.util.BasePresenter
import com.fuh.chattie.util.BaseView
import com.google.firebase.database.Query

/**
 * Created by lll on 23.08.2017.
 */
object ChatRoomsCotract {
    interface Presenter : BasePresenter {
        fun loadChatRooms()
    }

    interface View : BaseView<Presenter> {
        fun showChatRooms(query: Query)
    }
}