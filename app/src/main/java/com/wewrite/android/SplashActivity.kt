package com.wewrite.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.kakao.sdk.common.util.Utility
import com.wewrite.android.Login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        findKeyHash()
    }

    private fun findKeyHash() {
        var keyHash = Utility.getKeyHash(this)
        Log.d("KeyHash", keyHash)
    }


    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 1500)
    }
}