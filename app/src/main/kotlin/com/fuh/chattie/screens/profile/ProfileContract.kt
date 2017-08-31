package com.fuh.chattie.screens.profile

import com.fuh.chattie.model.CurrentUserChangeableModel
import com.fuh.chattie.model.User
import com.fuh.chattie.utils.BasePresenter
import com.fuh.chattie.utils.BaseView

/**
 * Created by lll on 17.08.2017.
 */
object ProfileContract {
    interface View : BaseView<Presenter> {
        fun showUser(user: User)
        fun showUserUpdateStart()
        fun showUserUpdateComplete()
        fun showUserUpdateFail()
}

    interface Presenter : BasePresenter {
        fun loadUser()
        fun updateUser(changedUser: CurrentUserChangeableModel)
    }
}