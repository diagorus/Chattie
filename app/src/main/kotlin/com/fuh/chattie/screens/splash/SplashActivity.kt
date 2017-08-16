package com.fuh.chattie.screens.splash

import android.os.Bundle
import android.content.Intent
import com.fuh.chattie.R
import com.fuh.chattie.screens.profile.ProfileActivity
import com.fuh.chattie.util.BaseActivity
import com.fuh.chattie.util.extentions.toast

/**
 * Created by lll on 11.08.2017.
 */
class SplashActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.splash_activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        toast("Splash!")
        finish()
    }
}