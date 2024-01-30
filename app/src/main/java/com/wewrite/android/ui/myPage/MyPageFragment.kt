package com.wewrite.android.ui.myPage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wewrite.android.R
import com.wewrite.android.api.data.com.wewrite.android.ui.commons.PostAdapter
import com.wewrite.android.api.model.BoardItem
import com.wewrite.android.api.model.UserResponse
import com.wewrite.android.api.repository.BookmarkRepository
import com.wewrite.android.api.repository.UserRepository
import com.wewrite.android.databinding.FragmentMyPageBinding
import com.wewrite.android.ui.MainActivity
import com.wewrite.android.ui.commons.PostGridDecoration
import com.wewrite.android.ui.group.GroupFragment
import kotlinx.coroutines.launch

class MyPageFragment : Fragment() {

    private lateinit var binding: FragmentMyPageBinding
    private lateinit var postList: List<BoardItem>
    private lateinit var userData: UserResponse.UserData
    private lateinit var userRepository: UserRepository
    private lateinit var bookmarkRepository: BookmarkRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)
        lifecycleScope.launch {
            try {
                postList = getLikePostList()
                userData = getUserData()
                setLikePostList()
                setUserProfile()
                setGroupManageButton()
            } catch (e: Exception) {
                Log.e("MyPageFragment", e.toString())
            }
        }

        return binding.root
    }

    private fun setLikePostList() {
        val recyclerViewList: RecyclerView = binding.rvLikePost
        recyclerViewList.layoutManager = GridLayoutManager(requireContext(),
            1, GridLayoutManager.VERTICAL, false)

        val postAdapter = PostAdapter(postList)
        recyclerViewList.adapter = postAdapter

        recyclerViewList.addItemDecoration(PostGridDecoration(16))
    }

    private fun setUserProfile() {
        binding.apply {
            myPageUserName.text = userData.userName
            myPageGroupCount.text = userData.numberOfMyGroups.toString()
            Glide.with(root.context)
                .load(userData.userImage)
                .placeholder(R.drawable.img_user_default)
                .error(R.drawable.img_user_default)
                .into(myPageUserImage)
        }
    }

    private suspend fun getLikePostList(): List<BoardItem> {
        bookmarkRepository = BookmarkRepository.create()

        return try {
            val response = bookmarkRepository.getBookmarkList()
            val boardList = response.data.map { it.boardList }
            boardList
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("MyPageFragment", e.toString())
            emptyList()
        }
    }

    private suspend fun getUserData(): UserResponse.UserData {
        userRepository = UserRepository.create()
        return try {
            val response = userRepository.getUserInfo()
            return response.data
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("MyPageFragment", e.toString())
            UserResponse.UserData(0, "", "", "", "")
        }
    }

    //binding.btnGroupMAnage를 누르면 MainActivity의 setFragment 함수를 호출하여 GroupManageFragment로 이동
    private fun setGroupManageButton() {
        binding.btnGroupMange.setOnClickListener {
            (activity as MainActivity).navigateTo(GroupFragment())
        }
    }
}

//@author: 이승민