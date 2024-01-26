import com.wewrite.android.api.APIFactory
import com.wewrite.android.api.data.GetOneBoardResponse
import com.wewrite.android.api.data.UpdateBoardRequest
import com.wewrite.android.api.service.BoardService

class BoardRepository(private val boardService: BoardService) {

    suspend fun getOneBoardByBoardId(token: String, boardId: Long): GetOneBoardResponse {
        return boardService.getOneBoard(token, boardId)
    }

    suspend fun updateBoard(
        token: String,
        boardId: Long,
        boardDTO: UpdateBoardRequest
    ): String {
        return boardService.updateBoard(token, boardId, boardDTO)
    }

    companion object {
        fun create(): BoardRepository {
            val groupService = APIFactory.getInstance().create(BoardService::class.java)
            return BoardRepository(groupService)
        }
    }
}

//@Author: 김동욱
