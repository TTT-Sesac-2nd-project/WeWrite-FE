import com.wewrite.android.api.APIFactory
import com.wewrite.android.api.data.GetOneBoardResponse
import com.wewrite.android.api.model.BoardRequest
import com.wewrite.android.api.model.BaseResponse
import com.wewrite.android.api.service.BoardService

class BoardRepository(private val boardService: BoardService) {


    companion object {
        fun create(): BoardRepository {
            val groupService = APIFactory.getInstance().create(BoardService::class.java)
            return BoardRepository(groupService)
        }
    }
    suspend fun getOneBoardByBoardId(token: String, boardId: Long): GetOneBoardResponse {
        return boardService.getOneBoard(token, boardId)
    }

    suspend fun updateBoard(
        token: String,
        boardId: Long,
        boardDTO: BoardRequest
    ): String {
        return boardService.updateBoard(token, boardId, boardDTO)
    }

    suspend fun deleteBoard(
        token: String,
        boardId : Long,

    ) : String {
        return boardService.deleteBoard(token, boardId)
    }

}

//@Author: 김동욱
