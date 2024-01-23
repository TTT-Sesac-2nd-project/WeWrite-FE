package com.wewrite.android.api.service

import com.wewrite.android.api.data.LoginResponse
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginService {
    @POST("user/issue-token")
    suspend fun kakaoLogin(
        @Header("access_token") access_token: String
    ): LoginResponse
}

//@author: 이승민