package com.fuh.chattie.screens.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.fuh.chattie.R
import com.fuh.chattie.model.Message
import com.fuh.chattie.model.datastore.ChatRoomsDataStore
import com.fuh.chattie.model.datastore.CurrentUserIdDataStore
import com.fuh.chattie.model.datastore.MessagesDataStore
import com.fuh.chattie.model.datastore.UsersDataStore
import com.fuh.chattie.utils.ui.BaseToolbarActivity
import com.fuh.chattie.utils.extentions.textValue
import com.google.firebase.database.FirebaseDatabase
import com.jakewharton.rxbinding2.support.v7.widget.scrollEvents
import kotlinx.android.synthetic.main.chat_activity.*
import timber.log.Timber


/**
 * Created by lll on 09.08.2017.
 */
class ChatActivity : BaseToolbarActivity(), ChatContract.View {

    companion object {
        private const val EXTRA_CHAT_ROOM_ID = "EXTRA_CHAT_ROOM_ID"

        fun newIntent(context: Context, chatRoomId: String): Intent =
                Intent(context, ChatActivity::class.java)
                        .apply {
                            putExtra(EXTRA_CHAT_ROOM_ID, chatRoomId)
                        }
    }

    override lateinit var presenter: ChatContract.Presenter

    private lateinit var chatMessageAdapter: ChatAdapter

    override fun getLayoutId(): Int = R.layout.chat_activity

    override fun ActionBar.init() {
        title = "Chat"
    }

    override fun showChatInitial(currentUserId: String, messages: List<Message>) {
        chatMessageAdapter = ChatAdapter(currentUserId, messages)

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
                .subscribe({
                    with(fabChatScrollToEnd) { if (it) { hide() } else { show() } }
                }, {
                    Timber.e(it)
                })

        fabChatScrollToEnd.setOnClickListener {
            rvChatMessages.smoothScrollToPosition(chatMessageAdapter.itemCount - 1)
        }
    }

    override fun showChatNewMessage(message: Message) {
        chatMessageAdapter.addMessage(message)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val chatRoomId = getChatRoomId()

        presenter = ChatPresenter(
                this,
                CurrentUserIdDataStore(this),
                MessagesDataStore(FirebaseDatabase.getInstance()),
                UsersDataStore(FirebaseDatabase.getInstance()),
                ChatRoomsDataStore(FirebaseDatabase.getInstance()),
                ChatPresenter.Parameters(chatRoomId)
        )
        presenter.start()

        ivChatSend.setOnClickListener {
            val messageText = etChatInput.textValue

            presenter.pushMessage(messageText)

            clearInput()
        }
    }

    override fun onStop() {
        super.onStop()

        presenter.stop()
    }

    private fun clearInput() {
        etChatInput.setText("")
    }

    private fun getChatRoomId(): String {
        return intent?.extras?.getString(EXTRA_CHAT_ROOM_ID) ?:
                throw IllegalArgumentException("EXTRA_CHAT_ROOM_ID not set")
    }
}