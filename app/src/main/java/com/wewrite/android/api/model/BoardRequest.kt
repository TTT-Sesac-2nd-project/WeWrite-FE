package com.wewrite.android.api.model

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.common.model.SdkIdentifier
import kotlinx.android.parcel.Parcelize

data class BoardEditRequest(
    val title: String,
    val content: String,
)

data class BoardRequest(
    val boardDTO: BoardData
) {
    @Parcelize
    data class BoardData(
        @SerializedName("boardTitle") val boardTitle: String,
        @SerializedName("boardLoc") val boardLoc: String,
        @SerializedName("boardContent") val boardContent: String,
        @SerializedName("groupId") val groupId: Int,
        @SerializedName("boardCreatedDate") val boardCreatedDate: String,
        @SerializedName("boardLat") val boardLat: String,
        @SerializedName("boardLng") val boardLng: String
    )

}

