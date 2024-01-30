package com.wewrite.android.api.model

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
        @SerializedName("groupId") val groupId: Long,
        @SerializedName("groupImageUrl") val groupImageUrl: String,
        @SerializedName("groupMemberCount") val groupMemberCount: Long,
        @SerializedName("groupName") val groupName: String,
    )
}

data class GroupPageResponse (
    val timeStamp: String,
    val code: Int,
    val status: String,
    val data: GroupPageData
) {
    @Parcelize
    data class GroupPageData(
        @SerializedName("boardList") val boardData: List<BoardItem>,
        @SerializedName("groupCode") val groupCode: String,
        @SerializedName("groupImageUrl") val groupImageUrl: String,
        @SerializedName("groupMemberCount") val groupMemberCount: Long,
        @SerializedName("groupName") val groupName: String,
    )
}

//@author: 이소민