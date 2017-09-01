package com.fuh.chattie.utils

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

/**
 * Created by lll on 01.09.2017.
 */
interface BaseChildEventListener : ChildEventListener {


    override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String) {
        //DO NOTHING
    }

    override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String) {
        //DO NOTHING
    }

    override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String) {
        //DO NOTHING
    }

    override fun onChildRemoved(dataSnapshot: DataSnapshot) {
        //DO NOTHING
    }

    override fun onCancelled(databaseError: DatabaseError) {
        //DO NOTHING
    }
}