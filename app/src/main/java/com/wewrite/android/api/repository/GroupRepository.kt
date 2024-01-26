import com.wewrite.android.api.APIFactory
import com.wewrite.android.api.data.GetOneBoardResponse
import com.wewrite.android.api.service.BoardService

class GroupRepository(private val boardService: BoardService) {
    // 제거해주세요
    suspend fun getOneGroupByGroupId(token: String, groupId: Long): GetOneBoardResponse {
        return boardService.getOneBoard(token, groupId)
    }

    //
    companion object {
        fun create(): GroupRepository {
            val groupService = APIFactory.getInstance().create(BoardService::class.java)
            return GroupRepository(groupService)
        }
    }
}

//@Author: 김동욱
