package com.fuh.chattie.screens.chatrooms

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.fuh.chattie.R
import com.fuh.chattie.model.ChatRoom
import com.fuh.chattie.model.datastore.ChatRoomDataStore
import com.fuh.chattie.model.datastore.CurrentUserIdDataStore
import com.fuh.chattie.screens.createchatroom.CreateChatRoomActivity
import com.fuh.chattie.util.BaseToolbarActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import kotlinx.android.synthetic.main.chat_activity.*
import kotlinx.android.synthetic.main.chatrooms_activity.*

/**
 * Created by lll on 23.08.2017.
 */
class ChatRoomsActivity : BaseToolbarActivity(), ChatRoomsCotract.View {

    companion object {
        fun newIntent(ctx: Context): Intent = Intent(ctx, ChatRoomsActivity::class.java)
    }

    override lateinit var presenter: ChatRoomsCotract.Presenter

    override fun getLayoutId(): Int = R.layout.chatrooms_activity

    override fun ActionBar.init() {
        title = "Chat rooms"
    }

    override fun showChatRooms(query: Query) {
        val chatRoomsAdapter = object : FirebaseRecyclerAdapter<ChatRoom, ChatRoomViewHolder>(
                ChatRoom::class.java,
                R.layout.chatroom_item,
                ChatRoomViewHolder::class.java,
                query
        ) {
            override fun populateViewHolder(viewHolder: ChatRoomViewHolder, model: ChatRoom, position: Int) {
                viewHolder.bind(model)
            }
        }

        val layoutManager = LinearLayoutManager(this)

        rvChatRooms.layoutManager = layoutMganager
        rvChatRooms.adapter = chatRoomsAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = ChatRoomsPresenter(
                this,
                CurrentUserIdDataStore(this),
                ChatRoomDataStore(FirebaseDatabase.getInstance())
        )
        presenter.start()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.chatrooms_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.chatrooms_menu_add -> {
//                val intent = CreateChatRoomActivity.newIntent(this)
//
//                startActivity(intent)
            }
        }
        return true
    }

    override fun onStop() {
        super.onStop()

        presenter.stop()
    }
}