package com.fuh.chattie.screens.splash

import com.fuh.chattie.model.datastore.CurrentUserAuthDataStore
import com.fuh.chattie.model.datastore.CurrentUserIdDataStore
import com.fuh.chattie.model.datastore.UsersDataStore
import timber.log.Timber

/**
 * Created by lll on 18.08.2017.
 */
class SplashPresenter(
        private val view: SplashContract.View,
        private val currentUserIdDataStore: CurrentUserIdDataStore,
        private val currentUserAuthDataSource: CurrentUserAuthDataStore,
        private val usersDataStore: UsersDataStore
) : SplashContract.Presenter {

    override fun start() {
        checkUser()
    }

    override fun checkUser() {
        currentUserAuthDataSource.getUser()
                .subscribe(
                        {
                            view.showUserSigned(it)
                        },
                        {
                            Timber.e(it)
                            view.showUserNotSigned()
                        }
                )
    }

    override fun saveCurrentUser() {
        currentUserAuthDataSource.getUser()
                .doOnSuccess { usersDataStore.postUser(it) }
                .doOnSuccess { currentUserIdDataStore.setCurrentUserId(it.id!!) }
                .subscribe(
                        { view.showUserSigned(it) },
                        {
                            Timber.e(it)
                            view.showUserNotSigned()
                        }
                )
    }
}