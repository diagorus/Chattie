package com.fuh.chattie.utils.validation

interface FormValidator {
    fun validateRegister(data: RegisterFormData): RegisterValidationResponse
    fun validateLogin(data: LoginFormData): LoginValidationResponse
}