package com.fuh.chattie.utils.validation

import com.fuh.chattie.utils.validation.FieldValidationResponse.VALID

data class LoginValidationResponse(
        val email: FieldValidationResponse = VALID,
        val password: FieldValidationResponse = VALID
 )

data class RegisterValidationResponse(
        val firstName: FieldValidationResponse = VALID,
        val lastName: FieldValidationResponse = VALID,
        val city: FieldValidationResponse = VALID,
        val email: FieldValidationResponse = VALID,
        val password: FieldValidationResponse = VALID,
        val repeatPassword: FieldValidationResponse = VALID
)

data class LoginFormData(
        val email: String,
        val password: String
)

data class RegisterFormData(
        val firstName: String,
        val lastName: String,
        val city: String,
        val email: String,
        val password: String,
        val repeatPassword: String
)
