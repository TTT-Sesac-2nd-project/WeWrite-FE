package com.wewrite.android.api.service

import com.wewrite.android.api.model.GroupResponse
import retrofit2.http.GET

interface GroupService {
    @GET("group")
    suspend fun getGroupList(): GroupResponse
}
