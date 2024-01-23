package com.wewrite.android.ui.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.wewrite.android.R
import com.wewrite.android.api.data.com.wewrite.android.ui.login.LoginModel

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            finish()
            checkLogin()
        }, 1500)
    }

    private fun checkLogin() {
            if (LoginModel(this).getToken() != "") {
                LoginModel(this).goToMainActivity(this)
                finish()
            } else {
                LoginModel(this).goToLoginActivity(this)
                finish()
            }
    }
}
