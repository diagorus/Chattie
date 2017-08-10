package com.fuh.chattie.screens.login

import com.google.firebase.auth.FirebaseAuth

/**
 * Created by lll on 10.08.2017.
 */
class LoginPresenter(val view: LoginContract.View, val firebaseAuth: FirebaseAuth) : LoginContract.Presenter {

    override fun checkIsUserLogged() {
        if (firebaseAuth.currentUser != null) {
            // logged
        } else {
            // not logged
        }
    }

    override fun attemptLogin(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        view.showLoginSuccessful(firebaseAuth.currentUser!!)
                    } else {
                        view.showLoginFailure()
                    }
                }
    }
}