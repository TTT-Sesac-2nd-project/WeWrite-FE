package com.wewrite.android.api.repository

import android.util.Log
import com.wewrite.android.api.APIFactory
import com.wewrite.android.api.model.BaseResponse
import com.wewrite.android.api.model.BookmarkResponse
import com.wewrite.android.api.service.BookmarkService

class BookmarkRepository(private val bookmarkService: BookmarkService) {
    suspend fun getBookmark(): BookmarkResponse {
        return bookmarkService.getBookmark()
    }

    suspend fun updateBookmark(boardId: Long): BaseResponse {
        return bookmarkService.updateBookmark(boardId)
    }

    companion object {
        fun create() : BookmarkRepository {
            val bookmarkService = APIFactory.getInstance().create(BookmarkService::class.java)
            return BookmarkRepository(bookmarkService)
        }
    }
}