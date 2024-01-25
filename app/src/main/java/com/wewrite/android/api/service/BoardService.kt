package com.wewrite.android.api.service

import com.wewrite.android.api.data.GetOneBoardResponse
import com.wewrite.android.api.data.UpdateBoardRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface BoardService {
    @GET("board/{boardId}")
    suspend fun getOneBoard(
        @Header("token") token: String,
        @Path("boardId") boardId: Long
    ): GetOneBoardResponse

    @PUT("board/{boardId}")
    suspend fun updateBoard(
        @Header("token") token: String,
        @Path("boardId") boardId: Long,
        @Body boardDTO: UpdateBoardRequest
    ): String


}

//@author: 김동욱