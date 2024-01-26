package com.wewrite.android.api.repository

import com.wewrite.android.api.APIFactory
import com.wewrite.android.api.model.BaseResponse
import com.wewrite.android.api.model.BookmarkResponse
import com.wewrite.android.api.service.BookmarkService

class BookmarkRepository(private val bookmarkService: BookmarkService) {
    suspend fun getBookmark(): List<BookmarkResponse> {
        return bookmarkService.getBookmark()
    }

    suspend fun updateBookmark(boardId: Int): BaseResponse {
        return bookmarkService.updateBookmark(boardId)
    }

    companion object {
        fun getBookmark() : BookmarkRepository {
            val bookmarkService = APIFactory.getInstance().create(BookmarkService::class.java)
            return BookmarkRepository(bookmarkService)
        }

        fun updateBookmark() : BookmarkRepository {
            val bookmarkService = APIFactory.getInstance().create(BookmarkService::class.java)
            return BookmarkRepository(bookmarkService)
        }
    }
}