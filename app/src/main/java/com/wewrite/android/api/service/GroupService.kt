package com.wewrite.android.api.service

import com.wewrite.android.api.model.BaseResponse
import com.wewrite.android.api.model.GroupCreateRequest
import com.wewrite.android.api.model.GroupJoinRequest
import com.wewrite.android.api.model.GroupPageResponse
import com.wewrite.android.api.model.GroupResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface GroupService {
    @GET("group")
    suspend fun getGroupList(): GroupResponse

    @Multipart
    @POST("group")
    suspend fun createGroup(
        @Part groupImage: MultipartBody.Part?,
        @Query("groupName") groupName: String
    ): BaseResponse


    @GET("group/{groupId}")
    suspend fun getGroupPage(
        @Path("groupId") groupId: Long
    ): GroupPageResponse

    @DELETE("group/{groupId}")
    suspend fun deleteGroup(
        @Path("groupId") groupId: Long
    ): BaseResponse

    @PATCH("group/{groupId}")
    suspend fun updateGroup(
        @Path("groupId") groupId: Long
    ): BaseResponse

    @POST("group/join")
    suspend fun joinGroup(
        @Body groupCodeData: GroupJoinRequest
    ): BaseResponse

    @DELETE("group/leave/{groupId}")
    suspend fun leaveGroup(
        @Path("groupId") groupId: Long
    ): BaseResponse

}
