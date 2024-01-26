package com.wewrite.android.api

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.wewrite.android.api.data.com.wewrite.android.ui.login.LoginController
import okhttp3.Interceptor
import okhttp3.Response
import com.wewrite.android.ui.login.LoginActivity

class JWTInterceptor: Interceptor {

    private val loginController = LoginController()

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = loginController.getToken()

        val request = chain.request().newBuilder()
            .header("token", "$accessToken")
            .build()

        val response = chain.proceed(request)

        if (response.code == 401) {
            loginController.saveToken("")
            goToLoginActivity(this.loginController.context as Activity?)
        }

        return response
    }

    private fun goToLoginActivity(activity: Activity?) {
        activity?.let {
            val intent = Intent(activity, LoginActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
