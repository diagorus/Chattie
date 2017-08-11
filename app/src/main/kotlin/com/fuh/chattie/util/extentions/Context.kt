package com.fuh.chattie.util.extentions

import android.content.Context
import android.widget.Toast
import android.content.ContentResolver
import android.net.Uri


/**
 * Created by lll on 10.08.2017.
 */
fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.resourceToUri(resId: Int): Uri {
    return Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
            resources.getResourcePackageName(resId) + '/' +
            resources.getResourceTypeName(resId) + '/' +
            resources.getResourceEntryName(resId)
    )
}