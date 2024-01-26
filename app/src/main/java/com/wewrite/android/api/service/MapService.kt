package com.wewrite.android.api.service

import com.wewrite.android.api.model.MapResponse
import retrofit2.http.GET

interface MapService {
    @GET("map/{groupId}")
    suspend fun getMapList(groupId: Int): MapResponse
}