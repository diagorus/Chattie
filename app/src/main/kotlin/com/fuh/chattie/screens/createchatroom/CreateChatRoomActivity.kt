package com.fuh.chattie.screens.createchatroom

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.Toast
import com.fuh.chattie.R
import com.fuh.chattie.model.User
import com.fuh.chattie.model.datastore.ChatRoomsDataStore
import com.fuh.chattie.model.datastore.CurrentUserIdDataStore
import com.fuh.chattie.model.datastore.UsersDataStore
import com.fuh.chattie.utils.ui.BaseToolbarActivity
import com.fuh.chattie.utils.extentions.toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.createchatroom_activity.*

/**
 * Created by lll on 23.08.2017.
 */
class CreateChatRoomActivity : BaseToolbarActivity(), CreateChatRoomContract.View {

    companion object {
        fun newIntent(ctx: Context): Intent = Intent(ctx, CreateChatRoomActivity::class.java)
    }

    override lateinit var presenter: CreateChatRoomContract.Presenter

    override fun getLayoutId(): Int = R.layout.createchatroom_activity

    override fun ActionBar.init() {
        title = "Create chat room"
        setDisplayHomeAsUpEnabled(true)
    }

    override fun showUsers(users: List<User>) {
        val usersAdapter = UsersAdapter(users) { selectedIndices ->
            val selectedUsers = users.slice(selectedIndices)

            presenter.createChatRoom(selectedUsers)
            finish()
        }

        val layoutManager = LinearLayoutManager(this)

        rvCreateChatRoomUserList.layoutManager = layoutManager
        rvCreateChatRoomUserList.adapter = usersAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = CreateChatRoomPresenter(
                this,
                CurrentUserIdDataStore(this),
                UsersDataStore(FirebaseDatabase.getInstance()),
                ChatRoomsDataStore(FirebaseDatabase.getInstance())
        )
        presenter.start()
    }

    override fun onStop() {
        super.onStop()

        presenter.stop()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when(item.itemId) {
                android.R.id.home -> {
                    finish()

                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
}