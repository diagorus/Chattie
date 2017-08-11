package com.fuh.chattie.screens.profile

import android.os.Bundle
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import com.fuh.chattie.R
import com.fuh.chattie.util.BaseAppCompatActivity
import com.fuh.chattie.util.extentions.resourceToUri
import com.fuh.chattie.util.extentions.toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.include_toolbar.*
import kotlinx.android.synthetic.main.profile_activity.*

/**
 * Created by lll on 11.08.2017.
 */
class ProfileActivity : BaseAppCompatActivity() {

    override fun getLayoutId(): Int {
        return R.layout.profile_activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        supportActionBar?.title = "Profile"

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
    }
}