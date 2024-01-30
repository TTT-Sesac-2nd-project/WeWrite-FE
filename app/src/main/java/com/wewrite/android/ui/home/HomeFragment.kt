package com.wewrite.android.ui.home

import BoardRepository
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wewrite.android.api.data.com.wewrite.android.ui.commons.PostAdapter
import com.wewrite.android.api.model.BoardItem
import com.wewrite.android.api.model.GroupResponse
import com.wewrite.android.api.repository.GroupRepository
import com.wewrite.android.databinding.FragmentHomeBinding
import com.wewrite.android.ui.commons.HomeGroupGridDecoration
import com.wewrite.android.ui.commons.PostGridDecoration
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), OnGroupClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var groupList: List<GroupResponse.GroupData>
    private lateinit var postList: List<BoardItem>
    private lateinit var groupRepository: GroupRepository
    private lateinit var boardRepository: BoardRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            try {
                groupList = getGroupList()
                postList = getBoardList()

                setGroupList()
                setPostList()
                setRecyclerDecoration()
            } catch (e: Exception) {
                Log.e("HomeFragment", e.toString())
            }
        }

        return binding.root
    }

    override fun onResume() {
        lifecycleScope.launch {
            try {
                postList = getBoardList()
                groupList = getGroupList()

                setPostList()
                setGroupList()
            } catch (e: Exception) {
                Log.e("HomeFragment", e.toString())
            }
        }
        super.onResume()
    }

    private suspend fun getGroupList(): List<GroupResponse.GroupData> {
        groupRepository = GroupRepository.create()
        try {
            val groupResponse = groupRepository.getGroupList()
            val groupList = groupResponse.data.toMutableList()
            groupList.add(0, createDefaultGroupData())

            return groupList
        } catch (e: Exception) {
            Log.e("groupResponse", e.toString())
        }
        return emptyList()
    }

    private suspend fun getBoardList(groupId: Long = 0): List<BoardItem> {
        boardRepository = BoardRepository.create()
        try {
            val boardResponse = boardRepository.getBoardList(groupId)
            return boardResponse.data.boardList
        } catch (e: Exception) {
            Log.e("boardResponse", e.toString())
        }
        return emptyList()
    }

    private fun setPostList() {
        // postList를 사용하여 UI 업데이트
        val recyclerViewList: RecyclerView = binding.recyclerPost
        recyclerViewList.layoutManager = GridLayoutManager(requireContext(),
            1, GridLayoutManager.VERTICAL, false)

        val postAdapter = PostAdapter(postList)
        recyclerViewList.adapter = postAdapter
    }

    private fun setRecyclerDecoration() {
        binding.recyclerPost.addItemDecoration(PostGridDecoration(16))
        binding.recyclerGroup.addItemDecoration(HomeGroupGridDecoration(16))
    }

    private fun setGroupList() {
        // groupList를 사용하여 UI 업데이트
        val recyclerViewList: RecyclerView = binding.recyclerGroup
        recyclerViewList.layoutManager = GridLayoutManager(requireContext(),
            1, GridLayoutManager.HORIZONTAL, false)

        val groupAdapter = HomeGroupAdapter(this, groupList)
        recyclerViewList.adapter = groupAdapter
    }

    private fun createDefaultGroupData(): GroupResponse.GroupData {
        return GroupResponse.GroupData(
            groupCode = "",
            groupId = 0,
            groupImageUrl = "", // 여기에 기본 이미지 URL을 넣어주세요
            groupMemberCount = 0,
            groupName = "전체"
        )
    }

    override fun onGroupClick(groupId: Long) {
        lifecycleScope.launch {
            try {
                postList = getBoardList(groupId)

                // postAdapter에 새로운 데이터 설정 후 UI 갱신
                binding.recyclerPost.adapter?.let { adapter ->
                    if (adapter is PostAdapter) {
                        adapter.setData(postList)
                        adapter.notifyDataSetChanged()
                    }
                }
            } catch (e: Exception) {
                Log.e("HomeFragment", e.toString())
            }
        }
    }
}

//@author: 이승민