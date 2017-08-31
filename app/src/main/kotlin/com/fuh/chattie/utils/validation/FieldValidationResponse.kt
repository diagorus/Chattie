package com.fuh.chattie.utils.validation

enum class FieldValidationResponse {
    EMPTY,
    ERROR,
    VALID;

    fun handleResults(empty: () -> Unit, error: () -> Unit, valid: () -> Unit = {}) {
        when(this) {
            EMPTY -> empty()
            ERROR -> error()
            VALID -> valid()
        }
    }
}