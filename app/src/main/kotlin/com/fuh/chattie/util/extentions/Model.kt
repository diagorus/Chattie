package com.fuh.chattie.util.extentions

import com.fuh.chattie.model.User
import com.google.gson.Gson

/**
 * Created by lll on 18.08.2017.
 */
inline fun <T> T?.toJson(): String? = Gson().toJson(this)

inline fun <reified T> String?.fromJson(): T? = Gson().fromJson(this, T::class.java)