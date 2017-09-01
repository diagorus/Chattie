package com.fuh.chattie.screens.createchatroom

import com.fuh.chattie.model.User
import com.fuh.chattie.utils.mvp.BasePresenter
import com.fuh.chattie.utils.mvp.BaseView

/**
 * Created by lll on 23.08.2017.
 */
object CreateChatRoomContract {
    interface Presenter : BasePresenter {
        fun loadUsers()
    }

    interface View : BaseView<Presenter> {
        fun showUsers(users: List<User>)
    }
}