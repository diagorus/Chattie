package com.fuh.chattie.screens.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.fuh.chattie.R
import com.google.firebase.auth.FirebaseUser

class LoginActivity: Activity(), LoginContract.View {

    companion object {
        fun newIntent(ctx: Context) = Intent(ctx, LoginActivity::class.java)
    }

    override var presenter: LoginContract.Presenter
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
    }

    override fun showEmailOrPasswordError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoginSuccessful(user: FirebaseUser) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoginFailure() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setProgress(active: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

