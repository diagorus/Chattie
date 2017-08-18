package com.fuh.chattie.screens.chat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.firebase.ui.auth.AuthUI
import com.fuh.chattie.R
import com.fuh.chattie.model.ChatMessage
import com.fuh.chattie.screens.profile.ProfileActivity
import com.fuh.chattie.util.BaseToolbarActivity
import com.fuh.chattie.util.extentions.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.jakewharton.rxbinding2.support.v7.widget.scrollEvents
import kotlinx.android.synthetic.main.chat_activity.*
import java.util.*


/**
 * Created by lll on 09.08.2017.
 */
class ChatActivity : BaseToolbarActivity() {

    companion object {
        private const val SIGN_IN_REQUEST_CODE = 200
    }

    private lateinit var chatMessageAdapter: ChatAdapter

    override fun getLayoutId(): Int = R.layout.chat_activity

    override fun ActionBar.init() {
        title = "Chat"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser

        if (user == null) {
            // Start sign in/sign up activity
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .build(),
                    SIGN_IN_REQUEST_CODE
            )
        } else {
            // User is already signed in. Therefore, display
            // a welcome Toast
            toast("Welcome ${user.displayName}")
            // Load chat room contents
            displayChatMessages()
        }

        ivChatSend.setOnClickListener {
            // Read the input field and push a new instance
            // of ChatMessage to the Firebase database
            FirebaseDatabase.getInstance()
                    .reference
                    .push()
                    .setValue(
                            ChatMessage(
                                    etChatInput.text.toString(),
                                    FirebaseAuth.getInstance()
                                            .currentUser!!
                                            .displayName,
                                    FirebaseAuth.getInstance()
                                            .currentUser!!
                                            .uid,
                                    Date().time
                            )
                    )

            // Clear the input
            etChatInput.setText("")
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SIGN_IN_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                toast("Successfully signed in. Welcome!")

                displayChatMessages()
            } else {
                toast("We couldn't sign you in. Please try again later.")

                // Close the app
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        chatMessageAdapter.cleanup()
    }

    private fun displayChatMessages() {
        val currentUser = FirebaseAuth.getInstance().currentUser!!
        val reference = FirebaseDatabase.getInstance().reference

        chatMessageAdapter = ChatAdapter(currentUser, reference)

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

        fabChatScrollToEnd.setOnClickListener {
            rvChatMessages.smoothScrollToPosition(chatMessageAdapter.itemCount - 1)
        }


        rvChatMessages.scrollEvents()
                .map {
                    Log.d("TAG", "layoutManager.findLastVisibleItemPosition() ${layoutManager.findLastVisibleItemPosition()} " +
                            "layoutManager.itemCount - 1 ${layoutManager.itemCount - 1}")
                    layoutManager.findLastVisibleItemPosition() == layoutManager.itemCount - 1
                }
                .subscribe {
                    if (it) {
                        fabChatScrollToEnd.hide()
                    } else {
                        fabChatScrollToEnd.show()
                    }
                }
    }
}