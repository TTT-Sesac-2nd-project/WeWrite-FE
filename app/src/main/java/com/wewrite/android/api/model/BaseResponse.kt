package com.wewrite.android.api.model

import com.google.gson.annotations.SerializedName

open class BaseResponse {
    @SerializedName("code") val code: Int = 0
    @SerializedName("status") val message: String? = null
}