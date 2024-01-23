package com.wewrite.android.api.data

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class LoginResponse (
    val timeStamp: String,
    val code: Int,
    val status: String,
    val data: List<UserData>
) {
    @Parcelize
    data class UserData(
        @SerializedName("accessToken") val accessToken: String,
        @SerializedName("userId") val userId: String,
        @SerializedName("userName") val userName: String,
        @SerializedName("userEmail") val userEmail: String,
        @SerializedName("userImage") val userImage: String,
    )
}
//@author: 이승민