package com.fuh.chattie.model.datastore

import android.content.Context

/**
 * Created by lll on 23.08.2017.
 */
class CurrentUserIdDataStore(ctx: Context) {

    companion object {
        private const val PREFERENCES_FILE_NAME = "VALUES"
        private const val PREFERENCES_USER_ID = "PREFERENCES_USER_ID"
    }

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