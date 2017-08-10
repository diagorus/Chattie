package com.fuh.chattie.screens.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.fuh.chattie.R
import com.fuh.chattie.util.extentions.replaceFragment

class LoginActivity: Activity(), LoginFlowContract.View {

    companion object {
        fun newIntent(ctx: Context) = Intent(ctx, LoginActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        showLoginMenu()
    }

    override fun showLogin() {
        val loginFragment: LoginFragment = LoginFragment.newInstance()
        replaceFragment(loginFragment, R.id.container)
    }

    override fun showLoginMenu() {
        val loginMenuFragment: LoginMenuFragment = LoginMenuFragment.newInstance()
        replaceFragment(loginMenuFragment, R.id.container)
    }

    override fun showRegister() {
        val registerFragment: RegisterFragment = RegisterFragment.newInstance()
        replaceFragment(registerFragment, R.id.container)
    }
}

