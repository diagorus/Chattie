package com.fuh.chattie.screens.login

import com.fuh.spotlight.model.ErrorResponse
import com.fuh.spotlight.model.User
import com.fuh.spotlight.ui.BaseView

object RegisterContract {
    interface View: BaseView<Presenter> {
        fun showFirstNameEmptyError()
        fun showFirstNameError()
        fun showLastNameEmptyError()
        fun showLastNameError()
        fun showCityEmptyError()
        fun showCityError()
        fun showEmailEmptyError()
        fun showEmailError()
        fun showPasswordEmptyError()
        fun showPasswordError()
        fun showRepeatPasswordEmptyError()
        fun showRepeatPasswordError()
        fun showNetworkError()
        fun showUnexpectedError()
        fun hideKeyboard()
        fun hideErrors()
        fun showErrorResponse(errorResponse: ErrorResponse)
        fun setProgress(active: Boolean)
        fun showWelcome(user: User)
    }
    interface Presenter {
        fun attemptRegister(firstName: String,
                            lastName: String,
                            city: String,
                            email: String,
                            password: String,
                            repeatPassword: String)
    }
}