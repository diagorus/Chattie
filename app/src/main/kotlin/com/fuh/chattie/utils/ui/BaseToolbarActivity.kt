package com.fuh.chattie.utils.ui

import android.os.Bundle
import android.support.v7.app.ActionBar
import kotlinx.android.synthetic.main.include_toolbar.*

/**
 * Created by lll on 16.08.2017.
 */
abstract class BaseToolbarActivity : BaseActivity() {

    abstract fun ActionBar.init()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar?.let { setSupportActionBar(toolbar) } ?:
                throw IllegalStateException("BaseToolbarActivity must have v7.Toolbar view with id: @id/toolbar")
        supportActionBar!!.init()
    }
}