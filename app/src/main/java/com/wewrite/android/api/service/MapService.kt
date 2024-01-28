package com.wewrite.android.api.service

import com.wewrite.android.api.model.MapResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MapService {
    @GET("map/{groupId}")
    suspend fun getMapList(
        @Path("groupId") groupId: Int
    ): MapResponse
}