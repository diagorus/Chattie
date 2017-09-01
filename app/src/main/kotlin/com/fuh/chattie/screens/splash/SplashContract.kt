package com.fuh.chattie.screens.splash

import com.fuh.chattie.model.User
import com.fuh.chattie.utils.mvp.BasePresenter
import com.fuh.chattie.utils.mvp.BaseView

/**
 * Created by lll on 18.08.2017.
 */
object SplashContract {
    interface View : BaseView<Presenter> {
        fun showUserSigned(user: User)
        fun showUserNotSigned()
    }

    interface Presenter : BasePresenter {
        fun checkUser()
        fun saveCurrentUser()
    }
}