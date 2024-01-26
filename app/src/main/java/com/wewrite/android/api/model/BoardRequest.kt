package com.wewrite.android.api.model

data class BoardEditRequest(
    val title: String,
    val content: String,
)

data class BoardRequest(
    val title: String,
    val content: String
)
