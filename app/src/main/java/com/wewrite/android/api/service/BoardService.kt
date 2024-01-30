package com.wewrite.android.api.service

import com.wewrite.android.api.model.BoardRequest
import com.wewrite.android.api.model.BaseResponse
import com.wewrite.android.api.model.BoardResponse
import com.wewrite.android.api.model.GetOneBoardResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface BoardService {
    @GET("board/{boardId}")
    suspend fun getOneBoard(
        @Path("boardId") boardId: Long
    ): GetOneBoardResponse

    @PUT("board/{boardId}")
    suspend fun updateBoard(
        @Path("boardId") boardId: Long,
        @Body boardDTO: BoardRequest
    ): BaseResponse

    @DELETE("board/{boardId}")
    suspend fun deleteBoard(
        @Path("boardId") boardId: Long
    ): BaseResponse

    @Multipart
    @POST("board/")
    suspend fun createBoard(
        @Part multipartFiles: MultipartBody.Part,
        @Part("boardDTO") boardDTO: BoardRequest.BoardData?
    ) : BaseResponse

    @GET("board/groups/{groupId}")
    suspend fun getBoardList(
        @Path("groupId") groupId: Long,
        @Query("sortedType") sortedType: String
    ): BoardResponse

}

//@author: 김동욱