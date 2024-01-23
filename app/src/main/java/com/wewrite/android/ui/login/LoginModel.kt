package com.wewrite.android.api.data.com.wewrite.android.ui.login

import android.content.Context
import android.content.Context.MODE_PRIVATE

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
}