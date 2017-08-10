package com.fuh.chattie.util.extentions

import android.content.Context
import android.support.design.widget.TextInputLayout
import android.view.View
import android.widget.EditText

val View.ctx: Context
    get() = context

fun View.slideExit() {
    if (translationY == 0f) animate().translationY(-height.toFloat())
}

fun View.slideEnter() {
    if (translationY < 0f) animate().translationY(0f)
}

var EditText.textValue: String
    get() = text.toString()
    set(v) {
        setText(v)
    }

var TextInputLayout.textValue: String
    get() = editText!!.textValue
    set(v) {
        editText!!.textValue = v
    }