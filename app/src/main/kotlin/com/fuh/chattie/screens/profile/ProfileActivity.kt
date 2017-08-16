package com.fuh.chattie.screens.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.ActionBar
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import com.fuh.chattie.R
import com.fuh.chattie.util.BaseActivity
import com.fuh.chattie.util.BaseToolbarActivity
import com.fuh.chattie.util.extentions.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.include_toolbar.*
import kotlinx.android.synthetic.main.profile_activity.*

/**
 * Created by lll on 11.08.2017.
 */
class ProfileActivity : BaseToolbarActivity() {

    companion object {
        fun newIntent(context: Context): Intent =
                Intent(context, ProfileActivity::class.java)
    }

    override fun getLayoutId(): Int = R.layout.profile_activity

    override fun ActionBar.init() {
        title = "Profile"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firebaseAuth = FirebaseAuth.getInstance()

        val photoUri = firebaseAuth.currentUser?.photoUrl ?: resourceToUri(R.drawable.no_avatar)

        Glide.with(this)
                .load(photoUri)
                .into(ivProfilePhoto)

        val userName = firebaseAuth.currentUser?.displayName ?: "Unknown"

        tvProfileName.text = userName

        btnProfileLogout.setOnClickListener {
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener {
                        toast("You have been signed out.")

                        // Close activity
                        finish()
                    }
        }

        ivProfilePhoto.setOnClickListener {
            showChooserPhotoFrom()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when(requestCode) {
                REQUEST_CAMERA -> {
//                    photoUri = data?.data
                }
                REQUEST_GALLERY -> {
//                    photoUri = filePathPhoto
                }
            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            when(requestCode) {
                REQUEST_PERMISSION_CAMERA -> startCamera()
                REQUEST_PERMISSION_STORAGE -> startGallery()
            }
        }
    }
}