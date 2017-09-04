package com.fuh.chattie.utils.extentions

import android.content.Context
import android.net.Uri
import com.fuh.chattie.R
import com.fuh.chattie.model.ChatRoomRaw
import com.fuh.chattie.model.MessageRaw
import com.fuh.chattie.model.User
import com.fuh.chattie.model.UserRaw
import com.google.gson.Gson

/**
 * Created by lll on 18.08.2017.
 */
inline fun <T> T?.toJson(): String? = Gson().toJson(this)

inline fun <reified T> String?.fromJson(): T? = Gson().fromJson(this, T::class.java)

fun User?.getNameOrDefault(ctx: Context): String =
        this?.name ?: ctx.getString(R.string.all_user_name_default)

fun User?.getPhotoUriOrDefault(ctx: Context): Uri =
        Uri.parse(this?.photoUrl) ?: ctx.resourceToUri(R.drawable.no_avatar)

fun MessageRaw?.toMap(): Map<String, Any?> {
    return mapOf(
            "userId" to this?.userId,
            "text" to this?.text,
            "timestamp" to this?.timestamp
    )
}

fun ChatRoomRaw?.toMap(): Map<String, Any?> {
    return mapOf(
            "title" to this?.title,
            "lastMessage" to this?.lastMessage,
            "members" to this?.members
    )
}