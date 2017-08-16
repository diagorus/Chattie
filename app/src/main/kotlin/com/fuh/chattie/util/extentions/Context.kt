package com.fuh.chattie.util.extentions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.widget.Toast
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.util.TypedValue
import com.fuh.chattie.R
import java.io.File


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

fun Context.dp(px: Float): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, resources.displayMetrics)

fun Context.dp(px: Int): Int =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px.toFloat(), resources.displayMetrics).toInt()

fun Context.checkPermission(permission: String): Boolean =
        applicationContext.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

fun Context.createBitmap(uri: Uri): Bitmap? =
        MediaStore.Images.Media.getBitmap(contentResolver, uri)

fun Context.createImageFile(): Uri? {
    val file = getFileInAppRoot("Images/Profile photo.jpg")

    return FileProvider.getUriForFile(this, packageName, file)
}

fun Context.getFileInAppRoot(name: String): File {
    val rootDir = File(Environment.getExternalStorageDirectory(), "/${getString(R.string.app_name)}")
    if (!rootDir.exists()) { rootDir.mkdir() }
    return File(rootDir, name)
}