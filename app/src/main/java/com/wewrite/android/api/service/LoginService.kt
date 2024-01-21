package com.wewrite.android.api.service

import com.wewrite.android.api.model.BaseResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {
    @POST("/user/login")
    suspend fun JWTCheck(
        @Query("access_token") accessToken: String,
    ): BaseResponse

}