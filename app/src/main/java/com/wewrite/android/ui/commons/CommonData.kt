package com.wewrite.android.api.data.com.wewrite.android.ui.commons
//
//data class CommonData()

data class PostData(
    val postImg: String,
    val postUser: String,
    val postUserImg: Int,
    val postGroup: String,
    val postTitle: String,
    val postLocation: String,
    val postLike: Boolean,
    val postReplyCount: Int,
    val postViewCount: Int,
    val postDate: String
)