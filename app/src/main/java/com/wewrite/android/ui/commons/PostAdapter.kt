package com.wewrite.android.api.data.com.wewrite.android.ui.commons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wewrite.android.R
import com.wewrite.android.api.model.BoardItem
import com.wewrite.android.databinding.RvPostBinding

class PostAdapter(var PostList: List<BoardItem>): RecyclerView.Adapter<PostAdapter.ViewHolder>() {
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

    inner class ViewHolder(private val binding: RvPostBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BoardItem) {
            binding.apply {
                binding.postGroupName.text = item.groupName
                binding.postTitle.text = item.boardTitle
                binding.postReplyCount.text = "댓글 ${item.boardCommentCount.toString()} · "
                binding.postViewCount.text = "조회수 ${item.boardViewCount.toString()}"
                binding.postDate.text = item.boardCreatedDate
                binding.postLocationText.text = item.boardLoc
                binding.postUserName.text = item.userName
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
    }
}