package com.fuh.chattie.screens.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.fuh.chattie.R
import com.fuh.chattie.utils.ui.BaseActivity
import com.fuh.chattie.utils.extentions.makeHyperlinkLike
import kotlinx.android.synthetic.main.login_activity.*

class LoginActivity: BaseActivity() {

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

