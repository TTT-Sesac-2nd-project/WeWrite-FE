package com.wewrite.android.api.model

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class BookmarkResponse (
    val timeStamp: String,
    val code: Int,
    val status: String,
    val data: BookmarkList
) {
    @Parcelize
    data class BookmarkList(
        @SerializedName("boardList") val boardItem: BoardItem
        )
}