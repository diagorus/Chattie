package com.fuh.chattie.screens.profile

import com.fuh.chattie.model.User
import com.fuh.chattie.model.UserDataSource
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by lll on 17.08.2017.
 */
class ProfilePresenter(val view: ProfileContract.View, val model: UserDataSource) : ProfileContract.Presenter {

    override fun start() {
        loadUser()
    }

    override fun loadUser() {
        model.getUser()
                .subscribe(
                        {
                            view.showUser(it)
                        },
                        {
                            view.showNoUserError()
                        }
                )


    }

    override fun updateUser(user: User) {
        model.updateUser(user)
                .doOnSubscribe {
                    view.showUserUpdateStart()
                }
                .subscribe(
                        {
                            view.showUserUpdateComplete()
                        }, {
                            view.showUserUpdateFail()
                        }
                )
    }
}