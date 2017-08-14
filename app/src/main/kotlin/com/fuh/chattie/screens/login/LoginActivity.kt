package com.fuh.chattie.screens.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.fuh.chattie.R
import com.fuh.chattie.util.BaseAppCompatActivity
import com.fuh.chattie.util.extentions.makeHyperlinkLike
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.login_activity.*

class LoginActivity: BaseAppCompatActivity() {

    companion object {
        fun newIntent(ctx: Context) = Intent(ctx, LoginActivity::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.login_activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvLoginForgotPassword.makeHyperlinkLike()
    }
}

