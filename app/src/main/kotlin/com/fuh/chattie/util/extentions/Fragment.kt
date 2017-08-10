package com.fuh.chattie.util.extentions

import android.app.Fragment
import android.content.Context
import android.widget.Toast

val Fragment.ctx: Context
    get() = activity
fun Fragment.showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(ctx, text, duration).show()
}
fun Fragment.showToast(resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(ctx, resId, duration).show()
}
fun Fragment.addFragment(fragment: Fragment, frameId: Int) {
    fragmentManager.beginTransaction().add(frameId, fragment).commit()
}
fun Fragment.replaceFragment(fragment: Fragment, frameId: Int) {
    fragmentManager.beginTransaction().replace(frameId, fragment).commit()
}