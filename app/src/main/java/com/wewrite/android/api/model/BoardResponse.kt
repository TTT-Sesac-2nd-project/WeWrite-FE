package com.wewrite.android.api.model

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class BoardResponse(
    val timeStamp: String,
    val code: Int,
    val status: String,
    val data: BoardData
)
data class GetOneBoardResponse(
    val timeStamp: String,
    val code: Int,
    val status: String,
    val data: BoardDetailData
)

@Parcelize
data class BoardData(
    val boardList: List<BoardItem>
)

@Parcelize
data class BoardDetailData(
    @SerializedName("boardTitle") val boardTitle: String,
    @SerializedName("boardContent") val boardContent: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("boardLoc") val boardLoc: String,
    @SerializedName("userImage") val userImage: String,
    @SerializedName("isWriter") val isWriter: Long,
    @SerializedName("boardImageList") val boardImageList: List<String>
) : Serializable

@Parcelize
data class BoardItem(
    @SerializedName("boardCommentCount") val boardCommentCount: Long,
    @SerializedName("boardCreatedDate") val boardCreatedDate: String,
    @SerializedName("boardId") val boardId: Long,
    @SerializedName("boardImage") val boardImage: String,
    @SerializedName("boardLoc") val boardLoc: String,
    @SerializedName("boardTitle") val boardTitle: String,
    @SerializedName("boardViewCount") val boardViewCount: Long,
    @SerializedName("groupName") val groupName: String,
    @SerializedName("isBookmarked") var isBookmarked: Int,
    @SerializedName("userImage") val userImage: String,
    @SerializedName("userName") val userName: String
)
