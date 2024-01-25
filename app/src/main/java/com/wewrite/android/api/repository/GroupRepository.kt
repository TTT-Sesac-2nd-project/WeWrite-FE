package com.wewrite.android.api.repository

import android.util.Log
import com.wewrite.android.api.APIFactory
import com.wewrite.android.api.model.GroupResponse
import com.wewrite.android.api.service.GroupService

class GroupRepository(private val groupService: GroupService) {

    suspend fun getGroupList() : GroupResponse {
        return groupService.getGroupList()
    }

    companion object {
        fun create() : GroupRepository {
            val groupService = APIFactory.getInstance().create(GroupService::class.java)
            return GroupRepository(groupService)
        }
    }
}