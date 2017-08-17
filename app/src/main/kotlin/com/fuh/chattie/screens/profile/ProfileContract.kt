package com.fuh.chattie.screens.profile

import com.fuh.chattie.screens.model.User
import com.fuh.chattie.util.BasePresenter
import com.fuh.chattie.util.BaseView

/**
 * Created by lll on 17.08.2017.
 */
object ProfileContract {
    interface View : BaseView<Presenter> {
        fun showUser(user: User)
    }

    interface Presenter : BasePresenter {
        fun loadUser()
    }
}