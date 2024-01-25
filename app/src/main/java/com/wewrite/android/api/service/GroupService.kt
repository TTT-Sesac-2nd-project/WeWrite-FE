package com.wewrite.android.api.service

import com.wewrite.android.api.data.GetOneBoardResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface GroupService {
    @GET("board/{boardId}")
    suspend fun getOneBoard(
        @Header("token") token: String,
        @Path("boardId") boardId: Long
    ): GetOneBoardResponse


}

//@author: 김동욱