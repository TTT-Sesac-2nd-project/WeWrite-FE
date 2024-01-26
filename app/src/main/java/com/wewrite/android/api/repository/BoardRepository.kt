import com.wewrite.android.api.model.BaseResponse
import com.wewrite.android.api.model.BoardRequest
import com.wewrite.android.api.service.BoardService

class BoardRepository(private val boardService: BoardService) {


    suspend fun getOneBoardByBoardId(token: String, boardId: Long): BaseResponse {
        return boardService.getOneBoard(token, boardId)
    }


    suspend fun updateBoard(
        token: String,
        boardId: Long,
        boardDTO: BoardRequest
    ): BaseResponse {
        return boardService.updateBoard(token, boardId, boardDTO)
    }

    suspend fun deleteBoard(
        token: String,
        boardId: Long,

        ): BaseResponse {
        return boardService.deleteBoard(token, boardId)
    }


}

//@Author: 김동욱