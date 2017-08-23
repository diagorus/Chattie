package com.fuh.chattie.screens.splash

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import com.firebase.ui.auth.AuthUI
import com.fuh.chattie.R
import com.fuh.chattie.model.User
import com.fuh.chattie.model.datastore.CurrentUserAuthDataStore
import com.fuh.chattie.model.datastore.CurrentUserIdDataStore
import com.fuh.chattie.model.datastore.UsersDataStore
import com.fuh.chattie.screens.chat.ChatActivity
import com.fuh.chattie.screens.chatrooms.ChatRoomsActivity
import com.fuh.chattie.util.BaseActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import timber.log.Timber

/**
 * Created by lll on 11.08.2017.
 */
class SplashActivity : BaseActivity(), SplashContract.View {

    companion object {
        private const val REQUEST_SIGN_IN = 200
    }

    override lateinit var presenter: SplashContract.Presenter

    override fun getLayoutId(): Int = R.layout.splash_activity

    override fun showUserSigned(user: User) {
        val intent = ChatRoomsActivity.newIntent(this)
        startActivity(intent)
        finish()
    }

    override fun showUserNotSigned() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .build(),
                REQUEST_SIGN_IN
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = SplashPresenter(
                this,
                CurrentUserIdDataStore(this),
                CurrentUserAuthDataStore(FirebaseAuth.getInstance()),
                UsersDataStore(FirebaseDatabase.getInstance())
        )
        presenter.start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                Timber.d("User signed in")

                presenter.saveCurrentUser()
            } else {
                Timber.d("Sign in error")

                // Close the app
                finish()
            }
        }
    }
}