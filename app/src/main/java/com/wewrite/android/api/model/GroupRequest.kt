package com.wewrite.android.api.model

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class GroupCreateRequest (
    @SerializedName("groupImage") var groupImage: MultipartBody.Part,
    @SerializedName("groupName") var groupName: String
)

data class GroupJoinRequest (
    @SerializedName("groupCode") var groupCode: String
)

//@author: 이소민