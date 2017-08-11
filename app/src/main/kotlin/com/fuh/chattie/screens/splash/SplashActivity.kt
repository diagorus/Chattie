package com.fuh.chattie.screens.splash

import android.os.Bundle
import android.content.Intent
import android.os.Handler
import android.os.SystemClock
import com.fuh.chattie.R
import com.fuh.chattie.screens.profile.ProfileActivity
import com.fuh.chattie.util.BaseAppCompatActivity
import com.fuh.chattie.util.extentions.toast
import java.util.concurrent.TimeUnit

/**
 * Created by lll on 11.08.2017.
 */
class SplashActivity : BaseAppCompatActivity() {

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