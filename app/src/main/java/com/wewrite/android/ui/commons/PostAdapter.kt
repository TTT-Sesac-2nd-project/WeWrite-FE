package com.wewrite.android.api.data.com.wewrite.android.ui.commons

import BoardRepository
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wewrite.android.R
import com.wewrite.android.api.model.BoardItem
import com.wewrite.android.api.repository.BookmarkRepository
import com.wewrite.android.databinding.RvPostBinding
import com.wewrite.android.ui.detail.DetailActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostAdapter(var PostList: List<BoardItem>): RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private lateinit var bookMarkRepository: BookmarkRepository
    private lateinit var boardRepository: BoardRepository

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = PostList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return PostList.size
    }

    fun setData(newData: List<BoardItem>) {
        PostList = newData
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: RvPostBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BoardItem) {
            setStarClick()
            showDetail()
            binding.apply {
                postGroupName.text = item.groupName
                postTitle.text = item.boardTitle
                postReplyCount.text = "댓글 ${item.boardCommentCount.toString()} · "
                postViewCount.text = "조회수 ${item.boardViewCount.toString()}"
                postDate.text = item.boardCreatedDate
                postLocationText.text = item.boardLoc
                postUserName.text = item.userName

                if (item.isBookmarked == 1) {
                    postStar.setImageResource(R.drawable.vi_star_true)
                } else {
                    postStar.setImageResource(R.drawable.vi_star_false)
                }

                Glide.with(root.context)
                    .load(item.userImage)
                    .placeholder(R.drawable.img_user_default)
                    .error(R.drawable.img_user_default)
                    .into(postUserImage)

                Glide.with(root.context)
                    .load(item.boardImage) // postImg가 이미지 URL인 경우
                    .placeholder(R.drawable.img_group_default) // 이미지 로딩 중에 보여질 placeholder 이미지 (선택 사항)
                    .error(R.drawable.img_group_default) // 이미지 로딩 실패 시 보여질 이미지 (선택 사항)
                    .into(postImage)
            }
        }

        fun showDetail() {
            binding.postImage.setOnClickListener {
                boardRepository = BoardRepository.create()
                try {
                    // 코루틴 빌더를 사용하여 getOneBoard 함수를 호출
                    GlobalScope.launch(Dispatchers.Main) {
                        try {
                            val boardResponse = boardRepository.getOneBoard(PostList[adapterPosition].boardId)
                            val boardDetailData = boardResponse.data
                            val intent = Intent(binding.root.context, DetailActivity::class.java)
                            intent.putExtra("boardDetailData", boardDetailData)
                            intent.putExtra("boardId", PostList[adapterPosition].boardId)
                            intent.putExtra("isBookmarked", PostList[adapterPosition].isBookmarked)
                            binding.root.context.startActivity(intent)
                        } catch (e: Exception) {
                            Log.e("boardResponse", e.toString())
                        }
                    }
                } catch (e: Exception) {
                    Log.e("CoroutineException", e.toString())
                }
            }
        }

        private fun setStarClick() {
            binding.postStar.setOnClickListener {
                bookMarkRepository = BookmarkRepository.create()
                if (PostList[adapterPosition].isBookmarked == 1) {
                    binding.postStar.setImageResource(R.drawable.vi_star_false)
                    PostList[adapterPosition].isBookmarked = 0
                } else {
                    binding.postStar.setImageResource(R.drawable.vi_star_true)
                    PostList[adapterPosition].isBookmarked = 1
                }

                try {
                    // 코루틴 빌더를 사용하여 updateBookmark 함수를 호출
                    GlobalScope.launch(Dispatchers.Main) {
                        try {
                            bookMarkRepository.updateBookmark(PostList[adapterPosition].boardId)
                        } catch (e: Exception) {
                            Log.e("boardResponse", e.toString())
                        }
                    }
                } catch (e: Exception) {
                    Log.e("CoroutineException", e.toString())
                }
            }
        }
    }
}

//@author: 이승민