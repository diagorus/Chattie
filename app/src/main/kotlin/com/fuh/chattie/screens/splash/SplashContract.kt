package com.fuh.chattie.screens.splash

import com.fuh.chattie.model.User
import com.fuh.chattie.util.BasePresenter
import com.fuh.chattie.util.BaseView
import com.google.firebase.auth.FirebaseUser

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