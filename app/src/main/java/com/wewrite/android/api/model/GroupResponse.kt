package com.wewrite.android.api.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class GroupResponse (
    val timeStamp: String,
    val code: Int,
    val status: String,
    val data: List<GroupData>
) {

    @Parcelize
    data class GroupData(
        @SerializedName("groupCode") val groupCode: String,
        @SerializedName("groupId") val groupId: Int,
        @SerializedName("groupImageUrl") val groupImageUrl: String,
        @SerializedName("groupMemberCount") val groupMemberCount: Int,
        @SerializedName("groupName") val groupName: String,
    )
}

data class GroupPageResponse (
    val timeStamp: String,
    val code: Int,
    val status: String,
    val data: List<GroupPageData>
) {
    @Parcelize
    data class GroupPageData(
        @SerializedName("boardList") val boardList: List<BoardList>,
        @SerializedName("groupCode") val groupCode: String,
        @SerializedName("groupImageUrl") val groupImageUrl: String,
        @SerializedName("groupMemberCount") val groupMemberCount: Int,
        @SerializedName("groupName") val groupName: String,
    ) {
        @Parcelize
        data class BoardList(
            @SerializedName("boardCommentCount") val boardCommentCount: Int,
            @SerializedName("boardCreatedDate") val boardCreatedDate: String,
            @SerializedName("boardId") val boardId: Int,
            @SerializedName("boardImage") val boardImage: String,
            @SerializedName("boardLoc") val boardLoc: String,
            @SerializedName("boardTitle") val boardTitle: String,
            @SerializedName("boardViewCount") val boardViewCount: Int,
            @SerializedName("bookmarked") val bookmarked: Boolean,
            @SerializedName("groupName") val groupName: String,
            @SerializedName("userImage") val userImage: String,
            @SerializedName("userName") val userName: String
        )
    }
}
