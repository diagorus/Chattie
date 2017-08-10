package com.fuh.chattie.screens.login

import com.fuh.spotlight.contract.LoginContract
import com.fuh.spotlight.di.scope.FragmentScope
import com.fuh.spotlight.model.*
import com.fuh.chattie.util.RetrofitException
import com.fuh.chattie.util.RetrofitException.Kind.*
import com.fuh.chattie.util.validation.FieldValidationResponse.VALID
import com.fuh.chattie.util.validation.FormValidator
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class LoginPresenter
    @Inject constructor(val view: LoginContract.View,
                        val model: SpotlightApi,
                        val validator: FormValidator): LoginContract.Presenter {

    companion object {
        const val GRANT_TYPE = "password"
        const val CLIENT_ID = "-1"
        const val CLIENT_SECRET = "-1"
    }

    override fun attemptLogin(email: String, password: String) {
        val validationResponse = validator.validateLogin(LoginFormData(email, password))

        validationResponse.email.handleResults(
                { view.showEmailEmptyError() },
                { view.showEmailError() },
                { Timber.i("Email valid") }
        )

        validationResponse.password.handleResults(
                { view.showPasswordEmptyError() },
                { view.showPasswordError() },
                { Timber.i("Password valid") }
        )

        if (validationResponse.let { it.email == VALID && it.password == VALID } ) {
            val authRequest = AuthRequest(GRANT_TYPE, CLIENT_ID, CLIENT_SECRET, email, password)
            login(authRequest)
        }
    }

    //TODO: use subscription to unsubscribe
    private fun login(authRequest: AuthRequest) {
        model.login(authRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    /** TODO: save AuthResponse to Preferences */
                }
                .observeOn(Schedulers.newThread())
                .flatMap {
                    model.getMyself(arrayOf("firstName"))
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            Timber.i(it.toString())
                            view.showWelcome(it)
                        },
                        {
                            Timber.e(it)
                            when(it) {
                                is RetrofitException -> {
                                    when(it.kind) {
                                        HTTP -> {
                                            val errorResponse =
                                                    it.getErrorBodyAs(ErrorResponse::class.java)

                                            Timber.i(errorResponse.toString())
                                            view.showErrorResponse(errorResponse)
                                        }
                                        NETWORK -> view.showNetworkError()
                                        UNEXPECTED -> view.showUnexpectedError()
                                    }
                                }
                                else -> view.showUnexpectedError()
                            }
                        })
    }
}