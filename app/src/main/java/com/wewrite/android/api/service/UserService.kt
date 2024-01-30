package com.wewrite.android.api.service

import com.wewrite.android.api.model.UserResponse
import retrofit2.http.GET

interface UserService {

    @GET("user")
    suspend fun getUserData(): UserResponse
}

//@author: 이승민