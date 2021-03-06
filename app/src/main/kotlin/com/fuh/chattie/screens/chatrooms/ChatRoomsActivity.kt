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
import com.fuh.chattie.model.datastore.ChatRoomsDataStore
import com.fuh.chattie.model.datastore.CurrentUserIdDataStore
import com.fuh.chattie.model.datastore.UsersDataStore
import com.fuh.chattie.screens.chat.ChatActivity
import com.fuh.chattie.screens.createchatroom.CreateChatRoomActivity
import com.fuh.chattie.screens.profile.ProfileActivity
import com.fuh.chattie.utils.ui.BaseToolbarActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import kotlinx.android.synthetic.main.chatrooms_activity.*

/**
 * Created by lll on 23.08.2017.
 */
class ChatRoomsActivity : BaseToolbarActivity(), ChatRoomsContract.View {

    companion object {
        fun newIntent(ctx: Context): Intent = Intent(ctx, ChatRoomsActivity::class.java)
    }

    override lateinit var presenter: ChatRoomsContract.Presenter
    private lateinit var chatRoomsAdapter: ChatRoomsAdapter

    override fun getLayoutId(): Int = R.layout.chatrooms_activity

    override fun ActionBar.init() {
        title = "Chat rooms"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = ChatRoomsPresenter(
                this,
                CurrentUserIdDataStore(this),
                UsersDataStore(FirebaseDatabase.getInstance()),
                ChatRoomsDataStore(FirebaseDatabase.getInstance())
        )
        presenter.start()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.chatrooms_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                R.id.chatrooms_menu_add -> {
                    val intent = CreateChatRoomActivity.newIntent(this)
                    startActivity(intent)

                    true
                }
                R.id.chatrooms_menu_profile -> {
                    val intent = ProfileActivity.newIntent(this)
                    startActivity(intent)

                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    override fun onStop() {
        super.onStop()

        presenter.stop()
    }

    override fun onDestroy() {
        super.onDestroy()

//        chatRoomsAdapter.cleanup()
    }

//    override fun showChatRooms(query: Query) {
//        chatRoomsAdapter = object : FirebaseRecyclerAdapter<ChatRoom, ChatRoomViewHolder>(
//                ChatRoom::class.java,
//                R.layout.chatroom_item,
//                ChatRoomViewHolder::class.java,
//                query
//        ) {
//            override fun populateViewHolder(viewHolder: ChatRoomViewHolder, model: ChatRoom, position: Int) {
//                viewHolder.bind(model) {
//                    val chatRoomId = chatRoomsAdapter.getRef(position).key
//
//                    val intent = ChatActivity.newIntent(this@ChatRoomsActivity, chatRoomId)
//                    startActivity(intent)
//                }
//            }
//        }
//
//        val layoutManager = LinearLayoutManager(this)
//
//        rvChatRooms.layoutManager = layoutManager
//        rvChatRooms.adapter = chatRoomsAdapter
//    }

    override fun showChatRoomsInitial(initial: List<ChatRoom>) {
        chatRoomsAdapter = ChatRoomsAdapter(initial)
    }

    override fun showChatRoomsChanges(changed: ChatRoom) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}