package com.wewrite.android.ui.home

data class GroupData(
    val groupImg: Int,
    val groupTitle: String,
)

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
