package com.fuh.chattie.model

import com.google.firebase.database.Query

/**
 * Created by lll on 18.08.2017.
 */
data class ChatData(val currentUser: User, val messageQuery: Query)