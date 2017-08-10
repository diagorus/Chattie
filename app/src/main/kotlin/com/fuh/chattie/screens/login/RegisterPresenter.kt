package com.fuh.chattie.screens.login

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
class RegisterPresenter
    @Inject constructor(val view: RegisterContract.View,
                        val model: SpotlightApi,
                        val validator: FormValidator): RegisterContract.Presenter {

    override fun attemptRegister(firstName: String, lastName: String, city: String,
                                 email: String, password: String, repeatPassword: String) {

        val registerObject = RegisterFormData(firstName, lastName, city, email, password, repeatPassword)
        val validationResponse = validator.validateRegister(registerObject)

        handleValidationResponse(validationResponse)

        val valid = validationResponse
                .let {
                    it.firstName == VALID &&
                    it.lastName == VALID &&
                    it.city == VALID &&
                    it.email == VALID &&
                    it.password == VALID &&
                    it.repeatPassword == VALID
                }

        if (valid) {
            val user = User(0, firstName, lastName, "", "", city, 0.0f, email, password)
            register(user)
        }
    }

    private fun register(user: User) {
        model.createUser(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            //TODO: go to login
                        },
                        {
                            Timber.e(it)
                            when(it) {
                                is RetrofitException -> {
                                    when(it.kind) {
                                        HTTP -> {
                                            val errorResponse =
                                                    it.getErrorBodyAs(ErrorResponse::class.java)

                                            Timber.d(errorResponse.toString())
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

    private fun handleValidationResponse(response: RegisterValidationResponse) =
            with(response) {
                this.firstName.handleResults(
                        { view.showFirstNameEmptyError() },
                        { view.showFirstNameError() },
                        { Timber.d("Name valid") })

                this.lastName.handleResults(
                        { view.showLastNameEmptyError() },
                        { view.showLastNameError() },
                        { Timber.d("Surname valid") })

                this.city.handleResults(
                        { view.showCityEmptyError() },
                        { view.showCityError() },
                        { Timber.d("City valid") })

                this.email.handleResults(
                        { view.showEmailEmptyError() },
                        { view.showEmailError() },
                        { Timber.d("Email valid") })

                this.password.handleResults(
                        { view.showPasswordEmptyError() },
                        { view.showPasswordError() },
                        { Timber.d("Password valid") })

                this.repeatPassword.handleResults(
                        { view.showRepeatPasswordEmptyError() },
                        { view.showRepeatPasswordError() },
                        { Timber.d("Repeat password valid") })
            }
}