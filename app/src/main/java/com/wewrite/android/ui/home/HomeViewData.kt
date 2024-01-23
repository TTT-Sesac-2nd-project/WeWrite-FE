package com.wewrite.android.ui.home

data class GroupData(
    val groupImg: Int,
    val groupTitle: String,
)

data class PostData(
    val postImg: Int,
    val postTitle: String,
    val postContent: String,
    val postDate: String,
    val postLike: Int,
    val postComment: Int,
    val postShare: Int,
)
