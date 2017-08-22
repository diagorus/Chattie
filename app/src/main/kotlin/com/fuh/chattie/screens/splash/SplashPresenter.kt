package com.fuh.chattie.screens.splash

import com.fuh.chattie.model.User
import com.fuh.chattie.model.datastore.CurrentUserDataStore
import com.fuh.chattie.model.datastore.UsersDataStore
import com.google.firebase.auth.FirebaseUser
import timber.log.Timber

/**
 * Created by lll on 18.08.2017.
 */
class SplashPresenter(
        private val view: SplashContract.View,
        private val currentUserDataSource: CurrentUserDataStore,
        private val usersDataStore: UsersDataStore
) : SplashContract.Presenter {

    override fun start() {
        checkUser()
    }

    override fun checkUser() {
        currentUserDataSource.getUser()
                .subscribe(
                        { view.showUserSigned(it) },
                        {
                            Timber.e(it)
                            view.showUserNotSigned()
                        }
                )
    }

    override fun saveCurrentUser() {
        currentUserDataSource.getUser()
                .doOnSuccess { usersDataStore.postUser(it) }
                .subscribe(
                        { view.showUserSigned(it) },
                        {
                            Timber.e(it)
                            view.showUserNotSigned()
                        }
                )
    }
}