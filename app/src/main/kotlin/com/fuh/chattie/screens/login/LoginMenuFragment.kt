package com.fuh.chattie.screens.login

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fuh.chattie.R
import com.fuh.spotlight.R
import com.fuh.spotlight.di.component.activity.LoginActivityComponent
import kotlinx.android.synthetic.main.fragment_login_menu.*

class LoginMenuFragment: Fragment(), LoginMenuContract.View {

    companion object {
        fun newInstance(): LoginMenuFragment = LoginMenuFragment()
    }
    //TODO: think about it, smells fishy
    val loginFlow = activity as LoginFlowContract.View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_login_menu, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginMenuSignIn.setOnClickListener {
            showLogin()
        }

        loginMenuSignUp.setOnClickListener {
            showRegister()
        }
    }

    override fun showLogin() {
        loginFlow.showLogin()
    }

    override fun showRegister() {
        loginFlow.showRegister()
    }
}