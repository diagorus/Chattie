package com.fuh.chattie.screens.createchatroom

import com.fuh.chattie.model.ChatRoomRaw
import com.fuh.chattie.model.User
import com.fuh.chattie.model.datastore.ChatRoomsDataStore
import com.fuh.chattie.model.datastore.CurrentUserIdDataStore
import com.fuh.chattie.model.datastore.UsersDataStore
import com.fuh.chattie.utils.mvp.BasePresenter
import com.fuh.chattie.utils.mvp.BaseRxPresenter
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

/**
 * Created by lll on 23.08.2017.
 */
class CreateChatRoomPresenter(
        private val view: CreateChatRoomContract.View,
        currentUserIdDataStore: CurrentUserIdDataStore,
        private val usersDataStore: UsersDataStore,
        private val chatRoomsDataStore: ChatRoomsDataStore
) : CreateChatRoomContract.Presenter {

    private val currentUserId = currentUserIdDataStore.getCurrentUserId()

    private val compositeDisposable = CompositeDisposable()

    override fun start() {
        loadUsers()
    }

    override fun loadUsers() {
        val allUsersDisposable = usersDataStore.getAllUsers()
                .filter {
                    it.id != currentUserId
                }
                .toList()
                .subscribe(
                        { view.showUsers(it) },
                        { Timber.e(it) }
                )
        compositeDisposable.add(allUsersDisposable)
    }

    override fun createChatRoom(members: List<User>) {
        val currentUserDisposable = usersDataStore.getUser(currentUserId)
                .subscribe({ (name, _) ->
                    val titleRaw = "$name - ${members.map { it.name }.joinToString()}"
                    val membersRaw = members.map { it.id!! to true }.plus(currentUserId to true).toMap()

                    val newChatRoom = ChatRoomRaw(title = titleRaw, members = membersRaw)
                    chatRoomsDataStore.postChatRoom(newChatRoom)
                }, {
                    Timber.e(it)
                })
        compositeDisposable.add(currentUserDisposable)
    }

    override fun stop() {
        compositeDisposable.clear()
    }
}