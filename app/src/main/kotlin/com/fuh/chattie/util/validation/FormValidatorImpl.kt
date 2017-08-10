package com.fuh.chattie.util.validation

import com.fuh.spotlight.model.*
import com.fuh.chattie.util.validation.FieldValidationResponse.*

//TODO: change name!!!
class FormValidatorImpl: FormValidator {
    override fun validateRegister(data: RegisterFormData): RegisterValidationResponse {
        val firstName = when {
            data.firstName.isNullOrEmpty() -> EMPTY
            else -> VALID
        }
        val lastName = when {
            data.lastName.isNullOrEmpty() -> EMPTY
            else -> VALID
        }
        val city = when {
            data.city.isNullOrEmpty() -> EMPTY
            else -> VALID
        }
        val email = when {
            data.email.isNullOrEmpty() -> EMPTY
            !isEmailValid(data.email) -> ERROR
            else -> VALID
        }
        val password = when {
            data.password.isNullOrEmpty() -> EMPTY
            !isPasswordValid(data.password) -> ERROR
            else -> VALID
        }
        val repeatPassword = when {
            data.repeatPassword.isNullOrEmpty() -> EMPTY
            !isPasswordsMatch(data.password, data.repeatPassword) -> ERROR
            else -> VALID
        }

        return RegisterValidationResponse(firstName, lastName, city, email, password, repeatPassword)
    }


    override fun validateLogin(data: LoginFormData): LoginValidationResponse {
        val email = when {
            data.email.isNullOrEmpty() -> EMPTY
            !isEmailValid(data.email) -> ERROR
            else -> VALID
        }
        val password = when {
            data.password.isNullOrEmpty() -> EMPTY
            !isPasswordValid(data.password) -> ERROR
            else -> VALID
        }

        return LoginValidationResponse(email, password)
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     *  ^                 # start-of-string
     *  (?=.*[0-9])       # a digit must occur at least once
     *  (?=.*[a-z])       # a lower case letter must occur at least once
     *  (?=.*[A-Z])       # an upper case letter must occur at least once
     *  (?=.*[@#$%^&+=])  # a special character must occur at least once
     *  (?=\S+$)          # no whitespace allowed in the entire string
     *  .{8,}             # anything, at least eight places though
     *  $                 # end-of-string
     */
    private fun isPasswordValid(password: String): Boolean {
        return Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$").matches(password)
    }

    private fun isPasswordsMatch(password: String, repeatPassword: String): Boolean {
        return password == repeatPassword
    }

}