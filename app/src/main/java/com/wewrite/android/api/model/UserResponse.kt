package com.wewrite.android.api.model

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class UserResponse (
    val timeStamp: String,
    val code: Int,
    val status: String,
    val data: UserData
    ) {
        @Parcelize
        data class UserData(
            @SerializedName("numberOfMyGroups") val numberOfMyGroups: Long,
            @SerializedName("userEmail") val userEmail: String,
            @SerializedName("userId") val userId: String,
            @SerializedName("userImage") val userImage: String,
            @SerializedName("userName") val userName: String,
        )
    }
