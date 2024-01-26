package com.wewrite.android.ui.group

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wewrite.android.api.data.com.wewrite.android.ui.commons.PostAdapter
import com.wewrite.android.api.data.com.wewrite.android.ui.commons.PostData
import com.wewrite.android.databinding.ActivityGroupPageBinding
import com.wewrite.android.ui.commons.CustomDialog
import com.wewrite.android.ui.commons.PostGridDecoration
import com.wewrite.android.ui.home.HomeFragment


class GroupPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGroupPageBinding
    private val postList: List<PostData> = HomeFragment.generateDummyPostData()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupPageBinding.inflate(layoutInflater)

        setGroupPostList()
        setMoreButton()
        setContentView(binding.root)
        
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