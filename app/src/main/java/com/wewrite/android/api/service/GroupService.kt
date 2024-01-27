package com.wewrite.android.api.service

import com.wewrite.android.api.model.BaseResponse
import com.wewrite.android.api.model.GroupCreateRequest
import com.wewrite.android.api.model.GroupJoinRequest
import com.wewrite.android.api.model.GroupPageResponse
import com.wewrite.android.api.model.GroupResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface GroupService {
    @GET("group")
    suspend fun getGroupList(): GroupResponse

    @POST("group")
    suspend fun createGroup(
        @Body groupData: GroupCreateRequest
    ): BaseResponse

    @GET("group/{groupId}")
    suspend fun getGroupPage(groupId: Int): GroupPageResponse

    @DELETE("group/{groupId}")
    suspend fun deleteGroup(groupId: Int): BaseResponse

    @PATCH("group/{groupId}")
    suspend fun updateGroup(groupId: Int): BaseResponse

    @POST("group/join")
    suspend fun joinGroup(
        @Body groupCodeData: GroupJoinRequest
    ): BaseResponse

    @DELETE("group/leave/{groupId}")
    suspend fun leaveGroup(groupId: Int): BaseResponse

}
