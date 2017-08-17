package com.fuh.chattie.screens.profile

import com.fuh.chattie.screens.model.User
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by lll on 17.08.2017.
 */
class ProfilePresenter(val view: ProfileContract.View) : ProfileContract.Presenter {

    override fun start() {
        loadUser()
    }

    override fun loadUser() {
        val firebaseUser = FirebaseAuth.getInstance().currentUser!!
        val user = User(firebaseUser.displayName, firebaseUser.photoUrl)

        view.showUser(user)
    }

    fun updateUserProfile(user: User) {

    }
}