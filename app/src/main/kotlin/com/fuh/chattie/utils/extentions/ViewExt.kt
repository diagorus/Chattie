package com.fuh.chattie.utils.extentions

import android.content.Context
import android.support.design.widget.TextInputLayout
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.URLSpan
import android.view.View
import android.widget.EditText
import android.widget.TextView

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

fun TextView.makeHyperlinkLike() {
    val ssb = SpannableStringBuilder()
            .apply {
                append(text)
                setSpan(URLSpan("#"), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

    setText(ssb, TextView.BufferType.SPANNABLE)
}