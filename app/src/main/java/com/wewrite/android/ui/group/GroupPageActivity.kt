package com.wewrite.android.ui.group

import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import com.wewrite.android.ui.MainActivity
import com.wewrite.android.ui.commons.CustomDialog
import com.wewrite.android.ui.commons.PostGridDecoration
import kotlinx.coroutines.launch
import kotlin.properties.Delegates


class GroupPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGroupPageBinding
    private lateinit var postList: List<BoardItem>
    private lateinit var groupRepository: GroupRepository
    private lateinit var groupCode: String

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

        binding.btnBack.setOnClickListener {
            finish()
        }

        setMoreButton()
        setInviteButton()
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
            groupCode = response.groupCode

            response.boardData
        } catch (e: Exception) {
            Log.e("GroupPageActivity", e.toString())
            emptyList()
        }

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
            CustomDialog(this).show("그룹 나가기") { success ->
                if (success) {
                    Toast.makeText(this, "그룹을 나갔습니다.", Toast.LENGTH_SHORT).show()
                    leaveGroup()
                    navigateToMainActivity()
                } else {
                    Toast.makeText(this, "그룹 나가기를 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun leaveGroup() {
        groupRepository = GroupRepository.create()
        lifecycleScope.launch {
            try {
                groupRepository.leaveGroup(groupId)
                navigateToMainActivity()
            } catch (e: Exception) {
                Log.e("GroupPageActivity", e.toString())
            }
        }
    }

    private fun navigateToMainActivity() {
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
        }
        finish()
    }

    private fun setInviteButton() {
        binding.btnInvite.setOnClickListener {
            //클립보드에 그룹코드 복사
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.text = groupCode
            Toast.makeText(this, "그룹코드가 복사되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

}