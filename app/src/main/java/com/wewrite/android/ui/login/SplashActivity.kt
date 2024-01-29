package com.wewrite.android.ui.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.wewrite.android.R
import com.wewrite.android.api.data.com.wewrite.android.ui.login.LoginController

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            finish()
            forceLogin()
            checkLogin()

        }, 1500)
    }

    private fun checkLogin() {
        if (LoginController().getToken() != "") {
            LoginController().goToMainActivity(this)
            finish()
        } else {
            LoginController().goToLoginActivity(this)
            finish()
        }
    }

    private fun forceLogin() {
        LoginController().saveToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhY2Nlc3MtdG9rZW4iLCJ0b2tlbiI6Ikhyc1I2ZmVBZXBHMjczdTMtbVg1OC1LcTBzbE54bmdSVFZNS0t3MGZBQUFCalVfSmg4QVdwaEhKendYSnF3IiwidXNlcklkIjoiMzI5MDc4NzEwNyIsImlhdCI6MTcwNjQ0MDY1MSwiZXhwIjozNTA2NDQwNjUxfQ.alRgITQ4HXdMc6iAkk1z-WoELCfljj2mi3LYj7d10lU")
        finish()
    }
}
