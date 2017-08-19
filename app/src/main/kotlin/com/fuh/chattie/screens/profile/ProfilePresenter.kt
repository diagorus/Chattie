package com.fuh.chattie.screens.profile

import android.net.Uri
import com.fuh.chattie.model.CurrentUserChangeableModel
import com.fuh.chattie.model.User
import com.fuh.chattie.model.currentuser.CurrentUserDataStore
import com.fuh.chattie.model.storage.ImageDataStore
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import timber.log.Timber

/**
 * Created by lll on 17.08.2017.
 */
class ProfilePresenter(
        private val view: ProfileContract.View,
        private val currentUserModel: CurrentUserDataStore,
        private val imageModel: ImageDataStore
        ) : ProfileContract.Presenter {

    override fun start() {
        loadUser()
    }

    override fun loadUser() {
        currentUserModel.getUser()
                .subscribe(
                        {
                            view.showUser(it)
                        },
                        {
                            Timber.e(it)
                        }
                )
    }

    override fun updateUser(changedUser: CurrentUserChangeableModel) {
        val currentUser = currentUserModel.getUser()

        currentUser
                .flatMap {
                    if (it.photoUri == changedUser.photoUri.toString()) {
                        Single.just(changedUser.photoUri!!)
                    } else {
                        imageModel.uploadImage(it, changedUser.photoUri!!)
                    }
                }
                .zipWith(
                        currentUser,
                        BiFunction { newUri: Uri, user: User ->
                            user.copy(photoUri = newUri.toString())
                        }
                )
                .flatMapCompletable { currentUserModel.updateUser(it) }
                .doOnSubscribe {
                    view.showUserUpdateStart()
                }
                .subscribe(
                        {
                            view.showUserUpdateComplete()
                        },
                        {
                            view.showUserUpdateFail()
                        }
                )
    }

}