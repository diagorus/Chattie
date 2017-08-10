package com.fuh.chattie.screens.login

import com.fuh.spotlight.model.ErrorResponse
import com.fuh.spotlight.model.User
import com.fuh.spotlight.ui.BaseView

object LoginContract {
    interface View: BaseView<Presenter> {
        fun showEmailEmptyError()
        fun showEmailError()
        fun showPasswordEmptyError()
        fun showPasswordError()
        fun showNetworkError()
        fun showUnexpectedError()
        fun hideKeyboard()
        fun hideErrors()
        fun showErrorResponse(errorResponse: ErrorResponse)
        fun setProgress(active: Boolean)
        fun showWelcome(user: User)
    }

    interface Presenter {
        fun attemptLogin(email: String, password: String)
    }
}