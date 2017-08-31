package com.fuh.chattie.utils.validation

class MockFormValidator: FormValidator {
    override fun validateRegister(data: RegisterFormData) = RegisterValidationResponse()

    override fun validateLogin(data: LoginFormData) = LoginValidationResponse()
}