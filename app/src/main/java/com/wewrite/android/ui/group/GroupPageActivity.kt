package com.wewrite.android.ui.group

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wewrite.android.R
import com.wewrite.android.api.data.com.wewrite.android.ui.commons.PostAdapter
import com.wewrite.android.api.model.BoardItem
import com.wewrite.android.api.repository.GroupRepository
import com.wewrite.android.databinding.ActivityGroupPageBinding
import com.wewrite.android.ui.commons.CustomDialog
import com.wewrite.android.ui.commons.PostGridDecoration
import kotlinx.coroutines.launch
import kotlin.properties.Delegates


class GroupPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGroupPageBinding
    private lateinit var postList: List<BoardItem>
    private lateinit var groupRepository: GroupRepository

    private var groupId by Delegates.notNull<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupPageBinding.inflate(layoutInflater)
        groupId = intent.getLongExtra("groupId", 0)
        Log.e("GroupPageActivity", groupId.toString())

        lifecycleScope.launch {
            try {
                postList = getGroupPage()
                setGroupPostList()
            } catch (e: Exception) {
                Log.e("GroupPageActivity", e.toString())
            }

        }

        setMoreButton()
        setContentView(binding.root)

    }

    private suspend fun getGroupPage(): List<BoardItem> {
        groupRepository = GroupRepository.create()
        return try {
            val response = groupRepository.getGroupPage(groupId).data
            binding.tvGroupName.text = response.groupName
            binding.tvGroupMemberCount.text = response.groupMemberCount.toString()
            Glide.with(this)
                .load(response.groupImageUrl)
                .placeholder(R.drawable.img_group_default)
                .error(R.drawable.img_group_default)
                .into(binding.ivGroupImage)

            response.boardData
        } catch (e: Exception) {
            Log.e("GroupPageActivity", e.toString())
            generatePostDummy(10)
        }

    }

    fun generatePostDummy(count: Int): List<BoardItem> {
        val dummyList = mutableListOf<BoardItem>()

        for (i in 1..count) {
            val dummyItem = BoardItem(
                boardCommentCount = i.toLong(),
                boardCreatedDate = "2024-01-27",
                boardId = i.toLong(),
                boardImage = "dummy_image_url_$i",
                boardLoc = "Dummy Location $i",
                boardTitle = "Dummy Title $i",
                boardViewCount = i.toLong(),
                groupName = "Dummy Group $i",
                isBookmarked = 0,
                userImage = "dummy_user_image_url_$i",
                userName = "Dummy User $i"
            )

            dummyList.add(dummyItem)
        }

        return dummyList
    }



    private fun setGroupPostList() {
        val recyclerViewList: RecyclerView = binding.rvGroupPost
        recyclerViewList.layoutManager = GridLayoutManager(
            this, 1, GridLayoutManager.VERTICAL, false
        )

        val postAdapter = PostAdapter(postList)
        recyclerViewList.adapter = postAdapter

        recyclerViewList.addItemDecoration(PostGridDecoration(16))
    }

    private fun setMoreButton() {
        binding.btnMore.setOnClickListener {
            CustomDialog(this).show("그룹 나가기")
        }
    }

}