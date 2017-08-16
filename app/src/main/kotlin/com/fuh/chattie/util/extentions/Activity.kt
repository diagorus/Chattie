package com.fuh.chattie.util.extentions

import android.Manifest
import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.view.inputmethod.InputMethodManager
import com.fuh.chattie.R

val Activity.ctx: Context
    get() = this

fun Activity.addFragment(fragment: Fragment, frameId: Int) {
    fragmentManager.beginTransaction().add(frameId, fragment).commit()
}
fun Activity.replaceFragment(fragment: Fragment, frameId: Int) {
    fragmentManager.beginTransaction().replace(frameId, fragment).commit()
}

fun Activity.hideKeyboard() =
    currentFocus?.let {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

