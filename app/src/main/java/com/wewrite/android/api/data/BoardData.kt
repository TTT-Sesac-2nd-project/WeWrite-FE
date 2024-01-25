package com.wewrite.android.api.data

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class GetOneBoardResponse(
    val timeStamp: String,
    val code: Int,
    val status: String,
    val data: BoardData
) {
    @Parcelize
    data class BoardData(
        @SerializedName("boardTitle") val boardTitle: String,
        @SerializedName("boardContent") val boardContent: String,
        @SerializedName("userName") val userName: String,
        @SerializedName("boardLoc") val boardLoc: String,
        @SerializedName("userImage") val userImage: String,
        @SerializedName("isWriter") val isWriter: Long,
        @SerializedName("boardImageList") val boardImageList: List<String>

    )
}