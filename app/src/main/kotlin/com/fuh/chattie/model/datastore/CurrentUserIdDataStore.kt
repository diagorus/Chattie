package com.fuh.chattie.model.datastore

import android.content.Context
import com.fuh.chattie.model.datastore.contracts.PREFERENCES_FILE_NAME
import com.fuh.chattie.model.datastore.contracts.PREFERENCES_USER_ID

/**
 * Created by lll on 23.08.2017.
 */
class CurrentUserIdDataStore(ctx: Context) {

    private val preferences by lazy {
        ctx.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
    }

    fun getCurrentUserId(): String =
            preferences.getString(PREFERENCES_USER_ID, "-1")

    fun setCurrentUserId(userId: String) {
        preferences.edit()
                .putString(PREFERENCES_USER_ID, userId)
                .apply()
    }
}