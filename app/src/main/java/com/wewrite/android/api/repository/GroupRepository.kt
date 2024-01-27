package com.wewrite.android.api.repository

import com.wewrite.android.api.APIFactory
import com.wewrite.android.api.model.BaseResponse
import com.wewrite.android.api.model.GroupCreateRequest
import com.wewrite.android.api.model.GroupJoinRequest
import com.wewrite.android.api.model.GroupPageResponse
import com.wewrite.android.api.model.GroupResponse
import com.wewrite.android.api.service.GroupService

class GroupRepository(private val groupService: GroupService) {

    suspend fun getGroupList(): GroupResponse {
        return groupService.getGroupList()
    }

    suspend fun createGroup(groupCreateRequest: GroupCreateRequest): BaseResponse {
        return groupService.createGroup(groupCreateRequest)
    }

    suspend fun getGroupPage(groupId: Long): GroupPageResponse {
        return groupService.getGroupPage(groupId)
    }

    suspend fun deleteGroup(groupId: Long): BaseResponse {
        return groupService.deleteGroup(groupId)
    }

    suspend fun updateGroup(groupId: Long): BaseResponse {
        return groupService.updateGroup(groupId)
    }

    suspend fun joinGroup(joinGroupCode: String): BaseResponse {
        var request = GroupJoinRequest(joinGroupCode)
        return groupService.joinGroup(request)
    }

    suspend fun leaveGroup(groupId: Long): BaseResponse {
        return groupService.leaveGroup(groupId)
    }

    companion object {
        fun create(): GroupRepository {
            val groupService = APIFactory.getInstance().create(GroupService::class.java)
            return GroupRepository(groupService)
        }
    }
}