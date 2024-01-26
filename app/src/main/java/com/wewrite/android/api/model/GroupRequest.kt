package com.wewrite.android.api.model

import com.google.gson.annotations.SerializedName

data class GroupCreateRequest (
    @SerializedName("groupImageUrl") val groupImageUrl: Int,
    @SerializedName("groupName") val groupName: String
)