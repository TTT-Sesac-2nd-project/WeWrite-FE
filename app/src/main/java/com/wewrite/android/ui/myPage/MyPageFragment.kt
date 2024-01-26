package com.wewrite.android.ui.myPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wewrite.android.api.data.com.wewrite.android.ui.commons.PostAdapter
import com.wewrite.android.api.model.BoardItem
import com.wewrite.android.databinding.FragmentMyPageBinding
import com.wewrite.android.ui.commons.PostGridDecoration

class MyPageFragment : Fragment() {

    private lateinit var binding: FragmentMyPageBinding
    private lateinit var postList: List<BoardItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)
        setLikePostList()

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

}