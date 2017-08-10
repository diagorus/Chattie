package com.fuh.chattie.screens.login

import com.fuh.chattie.util.BasePresenter
import com.fuh.chattie.util.BaseView
import com.google.firebase.auth.FirebaseUser

/**
 * Created by lll on 10.08.2017.
 */
object LoginContract {
    interface View: BaseView<Presenter> {
        fun showEmailOrPasswordError()
        fun showLoginSuccessful(user: FirebaseUser)
        fun showLoginFailure()
        fun setProgress(active: Boolean)
    }

    interface Presenter: BasePresenter {
        fun checkIsUserLogged()

        fun attemptLogin(email: String, password: String)
    }
}