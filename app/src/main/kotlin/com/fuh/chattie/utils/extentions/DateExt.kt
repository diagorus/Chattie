package com.fuh.chattie.utils.extentions

import android.text.format.DateFormat

/**
 * Created by lll on 04.09.2017.
 */
fun Long.toTimeString(): CharSequence = DateFormat.format("HH:mm", this)