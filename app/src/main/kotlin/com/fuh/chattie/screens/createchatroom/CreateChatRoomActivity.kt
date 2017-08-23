package com.fuh.chattie.screens.createchatroom

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.fuh.chattie.R
import com.fuh.chattie.model.User
import com.fuh.chattie.screens.chat.ChatAdapter
import com.fuh.chattie.util.BaseToolbarActivity
import com.google.firebase.database.Query
import com.jakewharton.rxbinding2.support.v7.widget.scrollEvents
import kotlinx.android.synthetic.main.chat_activity.*
import timber.log.Timber

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
    }

    override fun showUsers(query: Query) {
        val usersAdapter = object : FirebaseRecyclerAdapter<User, UserItemViewHolder>(
                User::class.java,
                R.layout.user_item,
                UserItemViewHolder::class.java,
                query
        ) {
            override fun populateViewHolder(viewHolder: UserItemViewHolder, model: User, position: Int) {
                viewHolder.bind(model)
            }
        }

        val layoutManager = LinearLayoutManager(this)

        rvChatMessages.layoutManager = layoutManager
        rvChatMessages.adapter = usersAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}