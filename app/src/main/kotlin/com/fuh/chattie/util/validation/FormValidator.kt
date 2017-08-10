package com.fuh.chattie.util.validation

interface FormValidator {
    fun validateRegister(data: RegisterFormData): RegisterValidationResponse
    fun validateLogin(data: LoginFormData): LoginValidationResponse
}