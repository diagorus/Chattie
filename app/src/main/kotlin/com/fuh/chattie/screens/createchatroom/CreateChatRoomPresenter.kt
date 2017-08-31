package com.fuh.chattie.screens.createchatroom

import com.fuh.chattie.model.datastore.CurrentUserIdDataStore
import com.fuh.chattie.model.datastore.UsersDataStore
import com.fuh.chattie.utils.BaseRxPresenter
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

/**
 * Created by lll on 23.08.2017.
 */
class CreateChatRoomPresenter(
        private val view: CreateChatRoomContract.View,
        private val currentUserIdDataStore: CurrentUserIdDataStore,
        private val usersDataStore: UsersDataStore
) : BaseRxPresenter(), CreateChatRoomContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun start() {
        loadUsers()
    }

    override fun loadUsers() {
        val currentUserId = currentUserIdDataStore.getCurrentUserId()

        val disposable = usersDataStore.getAllUsers()
                .filter {
                    it.id != currentUserId
                }
                .toList()
                .subscribe(
                        { view.showUsers(it) },
                        { Timber.e(it) }
                )

        compositeDisposable.add(disposable)
    }

    override fun stop() {
        compositeDisposable.clear()
    }
}