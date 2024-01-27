package com.wewrite.android.api.repository

import com.wewrite.android.api.APIFactory
import com.wewrite.android.api.model.UserResponse
import com.wewrite.android.api.service.UserService

class UserRepository(private val userService: UserService) {

    suspend fun getUserInfo(): UserResponse {
        return userService.getUserData()
    }

    companion object {
        fun create(): UserRepository {
            val userService = APIFactory.getInstance().create(UserService::class.java)
            return UserRepository(userService)
        }
    }
}