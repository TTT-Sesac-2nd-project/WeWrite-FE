package com.wewrite.android.api.repository

import com.wewrite.android.api.APIFactory
import com.wewrite.android.api.model.MapResponse
import com.wewrite.android.api.service.MapService

class MapRepository(private val mapService: MapService) {
    suspend fun getMapList(groupId: Int) : MapResponse {
        return mapService.getMapList(groupId)
    }

    companion object {
        fun create() : MapRepository {
            val mapService = APIFactory.getInstance().create(MapService::class.java)
            return MapRepository(mapService)
        }
    }
}