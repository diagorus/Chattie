package com.fuh.chattie.model

import android.content.Context
import android.net.Uri
import com.fuh.chattie.R
import com.fuh.chattie.util.extentions.resourceToUri

/**
 * Created by lll on 18.08.2017.
 */
fun User?.getNameOrDefault(ctx: Context): String =
        this?.name ?: ctx.getString(R.string.all_user_name_default)

fun User?.getPhotoUriOrDefault(ctx: Context): Uri =
        Uri.parse(this?.photoUrl) ?: ctx.resourceToUri(R.drawable.no_avatar)