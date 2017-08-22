package com.fuh.chattie.screens.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.fuh.chattie.R
import com.fuh.chattie.model.Message
import com.fuh.chattie.model.User
import com.fuh.chattie.model.datastore.CurrentUserDataStore
import com.fuh.chattie.screens.profile.ProfileActivity
import com.fuh.chattie.util.BaseToolbarActivity
import com.fuh.chattie.util.extentions.textValue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.Query
import com.jakewharton.rxbinding2.support.v7.widget.scrollEvents
import kotlinx.android.synthetic.main.chat_activity.*
import timber.log.Timber
import java.util.*


/**
 * Created by lll on 09.08.2017.
 */
class ChatActivity : BaseToolbarActivity(), ChatContract.View {

    companion object {
        fun newIntent(context: Context): Intent =
                Intent(context, ChatActivity::class.java)
    }

    override lateinit var presenter: ChatContract.Presenter

    private lateinit var chatMessageAdapter: ChatAdapter
    private lateinit var currentUser: User

    override fun getLayoutId(): Int = R.layout.chat_activity

    override fun ActionBar.init() {
        title = "Chat"
    }

    override fun showChat(currentUser: User, query: Query) {
        this.currentUser = currentUser
        chatMessageAdapter = ChatAdapter(currentUser, query)

        val layoutManager = LinearLayoutManager(this)
                .apply { stackFromEnd = true }

        chatMessageAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)

                val friendlyMessageCount = chatMessageAdapter.itemCount
                val lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition()
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 || positionStart >= friendlyMessageCount - 1 && lastVisiblePosition == positionStart - 1) {
                    rvChatMessages.scrollToPosition(positionStart)
                }
            }
        })

        rvChatMessages.layoutManager = layoutManager
        rvChatMessages.adapter = chatMessageAdapter
        rvChatMessages.scrollEvents()
                .map { with(layoutManager) { findLastVisibleItemPosition() == itemCount - 1 } }
                .subscribe(
                        {
                            with(fabChatScrollToEnd) {
                                if (it) {
                                    hide()
                                } else {
                                    show()
                                }
                            }
                        },
                        { Timber.e(it) }
                )

        fabChatScrollToEnd.setOnClickListener {
            rvChatMessages.smoothScrollToPosition(chatMessageAdapter.itemCount - 1)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = ChatPresenter(this, CurrentUserDataStore(FirebaseAuth.getInstance()))
        presenter.start()

        ivChatSend.setOnClickListener {
            val message = Message(currentUser.id, etChatInput.textValue, Date().time)

            presenter.pushMessage(message)

            clearInput()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.chat_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.chat_menu_profile -> {
                val intent = ProfileActivity.newIntent(this)

                startActivity(intent)
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        chatMessageAdapter.cleanup()
    }

    private fun clearInput() {
        etChatInput.setText("")
    }
}