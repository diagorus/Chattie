package com.fuh.chattie.screens.chat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.fuh.chattie.R
import com.fuh.chattie.screens.model.ChatMessage
import com.fuh.chattie.util.extentions.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.chat_activity.*
import java.util.*


/**
 * Created by lll on 09.08.2017.
 */
class ChatActivity : AppCompatActivity() {

    companion object {
        private const val SIGN_IN_REQUEST_CODE = 100
    }

    private lateinit var chatMessageAdapter: FirebaseRecyclerAdapter<ChatMessage, ChatMessageViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.chat_activity)

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

        fabChatSend.setOnClickListener {
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
        if (item.itemId == R.id.menu_sign_out) {
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener {
                        toast("You have been signed out.")

                        // Close activity
                        finish()
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
        chatMessageAdapter = object : FirebaseRecyclerAdapter<ChatMessage, ChatMessageViewHolder>(
                ChatMessage::class.java,
                R.layout.chat_item,
                ChatMessageViewHolder::class.java,
                FirebaseDatabase.getInstance().reference
        ) {
            override fun populateViewHolder(viewHolder: ChatMessageViewHolder, model: ChatMessage, position: Int) {
                viewHolder.bind(model)
            }
        }

        val layoutManager = LinearLayoutManager(this)

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

//        rvChatMessages.addOnLayoutChangeListener {
//            _: View,
//            _: Int, _: Int, _: Int, bottom: Int,
//            _: Int, _: Int, _: Int, oldBottom: Int ->
//
//            if (bottom < oldBottom) {
//                rvChatMessages.postDelayed({
//                    rvChatMessages.scrollToPosition(rvChatMessages.adapter.itemCount - 1)
//                }, 100)
//            }
//        }

        rvChatMessages.adapter = chatMessageAdapter
        rvChatMessages.layoutManager = layoutManager
    }
}