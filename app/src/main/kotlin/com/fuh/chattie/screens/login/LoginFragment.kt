package com.fuh.chattie.screens.login

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fuh.chattie.R
import com.fuh.chattie.util.extentions.hideKeyboard
import com.fuh.chattie.util.extentions.showToast
import com.fuh.chattie.util.extentions.textValue
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.include_progress.*


class LoginFragment: Fragment(), LoginContract.View {

    companion object {
        fun newInstance(): LoginFragment = LoginFragment()
    }

    override lateinit var presenter: LoginContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginSignIn.setOnClickListener {
            presenter.attemptLogin(loginEmailWrapper.textValue,
                    loginPasswordWrapper.textValue)
        }
    }

    override fun showEmailEmptyError() {
        loginEmailWrapper.error = getString(R.string.login_errorfieldempty)
    }

    override fun showEmailError() {
        loginEmailWrapper.error = getString(R.string.login_emailerror)
    }

    override fun showPasswordEmptyError() {
        loginPasswordWrapper.error = getString(R.string.login_errorfieldempty)
    }

    override fun showPasswordError() {
        loginPasswordWrapper.error = getString(R.string.login_passworderror)
    }

    override fun showNetworkError() {
        showToast(R.string.login_errornetwork)
    }

    override fun showUnexpectedError() {
        showToast(R.string.login_errorunexpected)
    }

    override fun hideKeyboard() {
        activity.hideKeyboard()
    }

    override fun hideErrors() {
        loginEmailWrapper.isErrorEnabled = false
        loginPasswordWrapper.isErrorEnabled = false
    }

    override fun showErrorResponse(errorResponse: ErrorResponse) {
        showToast(getString(R.string.login_errorresponse, errorResponse.message))
    }

    override fun showWelcome(user: User) {
        showToast(getString(R.string.login_successful, user.firstName))
    }

    override fun setProgress(active: Boolean) = if (active) { showProgress() } else { hideProgress() }

    private fun showProgress() {
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

        with(loginForm) {
            visibility = View.GONE
            animate().setDuration(shortAnimTime).alpha(0.0f)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            visibility = View.GONE
                        }
                    })
        }

        with(progressBar) {
            visibility = View.VISIBLE
            animate().setDuration(shortAnimTime).alpha(1.0f)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            visibility = View.VISIBLE
                        }
                    })
        }

    }

    private fun hideProgress() {
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

        with(loginForm) {
            visibility = View.VISIBLE
            animate().setDuration(shortAnimTime).alpha(1.0f)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            visibility = View.VISIBLE
                        }
                    })
        }

        with(progressBar) {
            visibility = View.GONE
            animate().setDuration(shortAnimTime).alpha(0.0f)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            visibility = View.GONE
                        }
                    })
        }
    }
}