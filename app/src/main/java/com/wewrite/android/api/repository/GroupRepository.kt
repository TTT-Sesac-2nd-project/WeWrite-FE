package com.wewrite.android.api.repository

import com.wewrite.android.api.APIFactory
import com.wewrite.android.api.model.BaseResponse
import com.wewrite.android.api.model.GroupCreateRequest
import com.wewrite.android.api.model.GroupPageResponse
import com.wewrite.android.api.model.GroupResponse
import com.wewrite.android.api.service.GroupService

class GroupRepository(private val groupService: GroupService) {

    suspend fun getGroupList() : GroupResponse {
        return groupService.getGroupList()
    }

    suspend fun createGroup(groupCreateRequest: GroupCreateRequest) : BaseResponse {
        return groupService.createGroup(groupCreateRequest)
    }

    suspend fun getGroupPage(groupId: Int) : GroupPageResponse {
        return groupService.getGroupPage(groupId)
    }

    suspend fun deleteGroup(groupId: Int) : BaseResponse {
        return groupService.deleteGroup(groupId)
    }

    suspend fun updateGroup(groupId: Int) : BaseResponse {
        return groupService.updateGroup(groupId)
    }

    suspend fun joinGroup() : BaseResponse {
        return groupService.joinGroup()
    }

    suspend fun leaveGroup(groupId: Int) : BaseResponse {
        return groupService.leaveGroup(groupId)
    }

    companion object {
        fun check() : GroupRepository {
            val groupService = APIFactory.getInstance().create(GroupService::class.java)
            return GroupRepository(groupService)
        }

        fun create() : GroupRepository {
            val groupService = APIFactory.getInstance().create(GroupService::class.java)
            return GroupRepository(groupService)
        }

        fun getGroupPage() : GroupRepository {
            val groupService = APIFactory.getInstance().create(GroupService::class.java)
            return GroupRepository(groupService)
        }

        fun deleteGroup() : GroupRepository {
            val groupService = APIFactory.getInstance().create(GroupService::class.java)
            return GroupRepository(groupService)
        }

        fun updateGroup() : GroupRepository {
            val groupService = APIFactory.getInstance().create(GroupService::class.java)
            return GroupRepository(groupService)
        }

        fun joinGroup() : GroupRepository {
            val groupService = APIFactory.getInstance().create(GroupService::class.java)
            return GroupRepository(groupService)
        }

        fun leaveGroup() : GroupRepository {
            val groupService = APIFactory.getInstance().create(GroupService::class.java)
            return GroupRepository(groupService)
        }
    }
}