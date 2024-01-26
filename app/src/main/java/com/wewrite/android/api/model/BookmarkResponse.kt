package com.wewrite.android.api.model

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class BookmarkResponse (
    val timeStamp: String,
    val code: Int,
    val status: String,
    val data: BookmarkData
) {
    @Parcelize
    data class BookmarkData(
        @SerializedName("bookmarkId") val bookmarkId: String,
        @SerializedName("boardList") val boardList: BoardList
    ){
        @Parcelize
        data class BoardList(
            @SerializedName("boardId") val boardId: String,
            @SerializedName("boardTitle") val boardTitle: String,
            @SerializedName("boardContent") val boardContent: String,
            @SerializedName("boardLoc") val boardLoc: String,
            @SerializedName("boardImageList") val boardImageList: List<String>,
            @SerializedName("boardLike") val boardLike: Long,
            @SerializedName("boardComment") val boardComment: Long,
            @SerializedName("boardDate") val boardDate: String,
            @SerializedName("boardWriter") val boardWriter: String,
            @SerializedName("boardWriterImage") val boardWriterImage: String,
            @SerializedName("isWriter") val isWriter: Long,
            @SerializedName("isLike") val isLike: Long
        )
    }

}