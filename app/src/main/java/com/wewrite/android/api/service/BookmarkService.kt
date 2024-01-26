package com.wewrite.android.api.service

import com.wewrite.android.api.model.BaseResponse
import com.wewrite.android.api.model.BookmarkResponse
import retrofit2.http.*
interface BookmarkService {
    @GET("bookmark")
    suspend fun getBookmark(): List<BookmarkResponse>

    @PUT("bookmark/{boardId}")
    suspend fun updateBookmark(@Path("boardId") boardId: Int): BaseResponse
}
