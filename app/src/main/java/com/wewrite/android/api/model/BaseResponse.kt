package com.wewrite.android.api.model

data class BaseResponse(
    val code: Int,
    val data: String?,
    val status: String,
    val timeStamp: String
)
