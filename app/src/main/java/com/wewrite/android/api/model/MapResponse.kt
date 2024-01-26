package com.wewrite.android.api.model

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class MapResponse (
    val code: Int,
    val data: MapData,
    val status: String,
    val timeStamp: String,
) {
    @Parcelize
    data class MapData(
        @SerializedName("mapList") val mapList: List<MapList>,
    ) {
        @Parcelize
        data class MapList(
            @SerializedName("boardContent") val boardContent: String,
            @SerializedName("boardCreatedDate") val boardCreatedDate: String,
            @SerializedName("boardId") val boardId: Int,
            @SerializedName("boardImage") val boardImage: String,
            @SerializedName("boardLat") val boardLat: String,
            @SerializedName("boardLng") val boardLng: String,
            @SerializedName("boardTitle") val boardTitle: String,
            @SerializedName("groupName") val groupName: String
        )
    }
}