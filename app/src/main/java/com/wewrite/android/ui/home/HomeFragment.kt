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

class HomeFragment : Fragment() {

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
                // 비동기로 데이터 가져오기
                groupList = getGroupList()
                postList = getBoardList()

                // UI 업데이트
                setGroupList()
                setPostList()
            } catch (e: Exception) {
                Log.e("HomeFragment", e.toString())
            }
        }

        return binding.root
    }

    private suspend fun getGroupList(): List<GroupResponse.GroupData> {
        groupRepository = GroupRepository.create()
        try {
            val groupResponse = groupRepository.getGroupList()
            return groupResponse.data
        } catch (e: Exception) {
            Log.e("groupResponse", e.toString())
        }
        return emptyList()
    }

    private suspend fun getBoardList(): List<BoardItem> {
        boardRepository = BoardRepository.create()
        try {
            val boardResponse = boardRepository.getBoardList(groupId = 2)
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

        recyclerViewList.addItemDecoration(PostGridDecoration(16))
    }

    private fun setGroupList() {
        // groupList를 사용하여 UI 업데이트
        val recyclerViewList: RecyclerView = binding.recyclerGroup
        recyclerViewList.layoutManager = GridLayoutManager(requireContext(),
            1, GridLayoutManager.HORIZONTAL, false)

        val groupAdapter = HomeGroupAdapter(groupList)
        recyclerViewList.adapter = groupAdapter

        recyclerViewList.addItemDecoration(HomeGroupGridDecoration(16))
    }
}