package com.fuh.chattie.screens.createchatroom

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.fuh.chattie.R
import com.fuh.chattie.model.User
import com.fuh.chattie.model.datastore.CurrentUserIdDataStore
import com.fuh.chattie.model.datastore.UsersDataStore
import com.fuh.chattie.util.BaseToolbarActivity
import com.fuh.chattie.util.extentions.toast
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
        val usersAdapter = UsersAdapter(users) {
            toast(it.toString(), Toast.LENGTH_LONG)
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
                UsersDataStore(FirebaseDatabase.getInstance())
        )
        presenter.start()
    }
}