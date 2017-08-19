package com.fuh.chattie.screens.splash

import com.fuh.chattie.model.currentuser.CurrentUserDataStore
import timber.log.Timber

/**
 * Created by lll on 18.08.2017.
 */
class SplashPresenter(
        private val view: SplashContract.View,
        private val currentUserDataSource: CurrentUserDataStore
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
}