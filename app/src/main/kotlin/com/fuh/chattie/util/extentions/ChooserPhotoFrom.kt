package com.fuh.chattie.util.extentions

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import com.fuh.chattie.R

/**
 * Created by lll on 16.08.2017.
 */
const val REQUEST_GALLERY = 100
const val REQUEST_CAMERA = 101

const val REQUEST_PERMISSION_CAMERA = 110
const val REQUEST_PERMISSION_STORAGE = 111


fun Activity.showChooserPhotoFrom() {
    val items = arrayOf(
            getString(R.string.photo_chooser_from_camera),
            getString(R.string.photo_chooser_from_gallery)
    )

    AlertDialog.Builder(this)
            .setTitle(R.string.photo_chooser_invitation)
            .setItems(items) { dialog, which ->
                if (which == 0) {
                    openCamera()
                } else {
                    openGallery()
                }
                dialog.dismiss()}
            .show()
}

fun Activity.openGallery() {
    if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
        startGallery()
    } else {
        requestPermissionStorage()
    }
}

fun Activity.startGallery() {
    val intent = Intent()
            .apply {
                type = "image/*"
                action = Intent.ACTION_PICK
                putExtra(Intent.EXTRA_LOCAL_ONLY, true)
            }

    startActivityForResult(
            Intent.createChooser(intent, getString(R.string.photo_chooser_from_choose_gallery_program)),
            REQUEST_GALLERY
    )
}

fun Activity.openCamera() {
    if (checkPermission(Manifest.permission.CAMERA) &&
            checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
        startCamera()
    } else {
        requestPermissionCamera()
    }
}

fun Activity.startCamera() {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

    if (intent.resolveActivity(packageManager) != null) {
        val resInfoList = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        val filePathPhoto = createImageFile()

        if (filePathPhoto != null) {
            resInfoList.forEach {
                val packageName = it.activityInfo.packageName
                grantUriPermission(
                        packageName,
                        filePathPhoto,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, filePathPhoto)

            ActivityCompat.startActivityForResult(this, intent, REQUEST_CAMERA, null)
        }
    }
}

fun Activity.requestPermissionStorage() {
    if (!checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
        ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_PERMISSION_STORAGE
        )
    }
}

fun Activity.requestPermissionCamera() {
    if (!checkPermission(Manifest.permission.CAMERA)) {
        ActivityCompat.requestPermissions(
                this,
                arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_PERMISSION_CAMERA
        )
    }
}