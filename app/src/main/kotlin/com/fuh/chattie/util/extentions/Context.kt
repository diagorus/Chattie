package com.fuh.chattie.util.extentions

import android.content.Context
import android.widget.Toast

/**
 * Created by lll on 10.08.2017.
 */
fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}