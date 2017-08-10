package com.fuh.chattie.screens.login

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fuh.spotlight.R
import com.fuh.spotlight.di.component.activity.LoginActivityComponent
import com.fuh.spotlight.di.module.fragment.RegisterFragmentModule
import com.fuh.spotlight.model.ErrorResponse
import com.fuh.spotlight.model.User
import kotlinx.android.synthetic.main.include_progress.*
import javax.inject.Inject

class RegisterFragment : BaseFragment(), RegisterContract.View {

    companion object {
        fun newInstance(): RegisterFragment = RegisterFragment()
    }

    @Inject override lateinit var presenter: RegisterContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerSignUp.setOnClickListener {
            presenter.attemptRegister(registerFirstNameWrapper.textValue,
                    registerLastNameWrapper.textValue,
                    registerCityWrapper.textValue,
                    registerEmailWrapper.textValue,
                    registerPasswordWrapper.textValue,
                    registerRepeatPasswordWrapper.textValue)
        }
    }

    override fun setupDependencies() {
        val activityComponent = getActivityComponent(LoginActivityComponent::class.java)

        val component = activityComponent.registrationFragmentComponentBuilder()
                .loginFragmentModule(RegisterFragmentModule(this))
                .build()

        component.inject(this)
    }

    override fun showFirstNameEmptyError() {
        registerFirstNameWrapper.error = getString(R.string.register_errorfieldempty)
    }

    override fun showFirstNameError() {
        registerFirstNameWrapper.error = getString(R.string.register_firstnameerror)
    }

    override fun showLastNameEmptyError() {
        registerLastNameWrapper.error = getString(R.string.register_errorfieldempty)
    }

    override fun showLastNameError() {
        registerLastNameWrapper.error = getString(R.string.register_lastnameerror)
    }

    override fun showCityEmptyError() {
        registerCityWrapper.error = getString(R.string.register_errorfieldempty)
    }

    override fun showCityError() {
        registerCityWrapper.error = getString(R.string.register_cityerror)
    }

    override fun showEmailEmptyError() {
        registerEmailWrapper.error = getString(R.string.register_errorfieldempty)
    }

    override fun showEmailError() {
        registerEmailWrapper.error = getString(R.string.register_emailerror)
    }

    override fun showPasswordEmptyError() {
        registerPasswordWrapper.error = getString(R.string.register_errorfieldempty)
    }

    override fun showPasswordError() {
        registerEmailWrapper.error = getString(R.string.register_passworderror)
    }

    override fun showRepeatPasswordEmptyError() {
        registerRepeatPasswordWrapper.error = getString(R.string.register_errorfieldempty)
    }

    override fun showRepeatPasswordError() {
        registerRepeatPasswordWrapper.error = getString(R.string.register_repeatpassworderror)
    }

    override fun hideKeyboard() {
        activity.hideKeyboard()
    }

    override fun hideErrors() {
        registerFirstNameWrapper.isErrorEnabled = false
        registerLastNameWrapper.isErrorEnabled = false
        registerCityWrapper.isErrorEnabled = false
        registerEmailWrapper.isErrorEnabled = false
        registerPasswordWrapper.isErrorEnabled = false
        registerRepeatPasswordWrapper.isErrorEnabled = false
    }

    override fun showNetworkError() {
        showToast(R.string.register_errornetwork)
    }

    override fun showUnexpectedError() {
        showToast(R.string.register_errorunexpected)
    }

    override fun showErrorResponse(errorResponse: ErrorResponse) {
        showToast(getString(R.string.register_errorresponse, errorResponse.message))
    }

    override fun showWelcome(user: User) {
        showToast(getString(R.string.register_successful, user.firstName))
    }

    override fun setProgress(active: Boolean) = if (active) { showProgress() } else { hideProgress() }

    private fun showProgress() {
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

        with(registerForm) {
            visibility = android.view.View.GONE
            animate().setDuration(shortAnimTime).alpha(0.0f)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            visibility = android.view.View.GONE
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

        with(registerForm) {
            visibility = android.view.View.VISIBLE
            animate().setDuration(shortAnimTime).alpha(1.0f)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            visibility = android.view.View.VISIBLE
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