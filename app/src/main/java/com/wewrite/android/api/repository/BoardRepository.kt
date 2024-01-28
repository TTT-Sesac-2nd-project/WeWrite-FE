import com.wewrite.android.api.APIFactory
import com.wewrite.android.api.model.BaseResponse
import com.wewrite.android.api.model.BoardRequest
import com.wewrite.android.api.model.BoardResponse
import com.wewrite.android.api.model.GetOneBoardResponse
import com.wewrite.android.api.service.BoardService
import okhttp3.MultipartBody
import retrofit2.http.Multipart

class BoardRepository(private val boardService: BoardService) {


    suspend fun getBoardList(groupId: Long): BoardResponse{
        return boardService.getBoardList(groupId, "recent")
    }

    suspend fun getOneBoard(boardId: Long): GetOneBoardResponse {
        return boardService.getOneBoard(boardId)
    }

    suspend fun createBoard(boardData: BoardRequest.BoardData, boardImage: MultipartBody.Part?): BaseResponse {
        return boardService.createBoard(boardImage, boardData)
    }


    suspend fun updateBoard(
        boardId: Long,
        boardDTO: BoardRequest
    ): BaseResponse {
        return boardService.updateBoard(boardId, boardDTO)
    }

    suspend fun deleteBoard(
        boardId: Long,
        ): BaseResponse {
        return boardService.deleteBoard(boardId)
    }

    companion object
    {
        fun create(): BoardRepository {
            val boardService = APIFactory.getInstance().create(BoardService::class.java)
            return BoardRepository(boardService)
        }
    }
}

//@Author: 김동욱