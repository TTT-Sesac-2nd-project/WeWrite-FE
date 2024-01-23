package com.wewrite.android.api.data.com.wewrite.android.ui.login

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.wewrite.android.ui.MainActivity
import com.wewrite.android.ui.login.LoginActivity

class LoginModel(private val context: Context){
    fun saveToken(token: String) {
        val pref = context.getSharedPreferences("pref", MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("accessToken", token)
        editor.commit()
    }

    fun getToken(): String {
        val pref = context.getSharedPreferences("pref", MODE_PRIVATE)
        return pref.getString("accessToken", "").toString()
    }

    fun goToLoginActivity(nowActivity: Activity) {
        val intent = Intent(nowActivity, LoginActivity::class.java)
        nowActivity.startActivity(intent)
    }

    //토큰 확인 성공시 메인 화면으로 가는 함수
    fun goToMainActivity(nowActivity: Activity) {
        val intent = Intent(nowActivity, MainActivity::class.java)
        nowActivity.startActivity(intent)
    }
}