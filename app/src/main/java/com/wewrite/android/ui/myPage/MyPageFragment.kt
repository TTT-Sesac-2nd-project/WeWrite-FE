package com.wewrite.android.ui.myPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wewrite.android.R
import com.wewrite.android.api.data.com.wewrite.android.ui.commons.PostAdapter
import com.wewrite.android.api.data.com.wewrite.android.ui.commons.PostData
import com.wewrite.android.databinding.FragmentMyPageBinding
import com.wewrite.android.ui.commons.PostGridDecoration

class MyPageFragment : Fragment() {

    private lateinit var binding: FragmentMyPageBinding
    private val postList: List<PostData> = generateDummyPostData()

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

    private fun generateDummyPostData(): List<PostData> {
        return listOf(
            PostData("https://tttimagebucket.s3.ap-northeast-2.amazonaws.com/board/31dbde96-60d7-4790-a150-de3fcd38687c.jpeg",
                "이승민", R.drawable.img_user_default, "그룹1", "제육 맛집 다녀온 날", "압구정 김밥 장한평점",
                true, 4, 12, "2024.01.17"),
            PostData("https://tttimagebucket.s3.ap-northeast-2.amazonaws.com/board/31dbde96-60d7-4790-a150-de3fcd38687c.jpeg",
                "김동욱", R.drawable.img_group_default, "그룹2", "돈까스 맛집 다녀온 날", "성수 김밥 장한평점",
                true, 1, 123, "2024.01.07"),
            PostData("https://tttimagebucket.s3.ap-northeast-2.amazonaws.com/long_road.jpeg",
                "이소민", R.drawable.img_user_default, "그룹3", "라면 맛집 다녀온 날", "홍대 김밥 장한평점",
                true, 5, 2, "2023.03.17"),
            PostData("https://tttimagebucket.s3.ap-northeast-2.amazonaws.com/board/31dbde96-60d7-4790-a150-de3fcd38687c.jpeg",
                "장원영", R.drawable.img_user_default, "그룹4", "치킨 맛집 다녀온 날", "합정 김밥 장한평점",
                true, 8, 22, "2022.05.23"),
            PostData("https://tttimagebucket.s3.ap-northeast-2.amazonaws.com/board/31dbde96-60d7-4790-a150-de3fcd38687c.jpeg",
                "카리나", R.drawable.img_user_default, "그룹5", "피자 맛집 다녀온 날", "서울대 김밥 장한평점",
                true, 14, 44, "2023.03.17"),
            PostData("https://tttimagebucket.s3.ap-northeast-2.amazonaws.com/board/31dbde96-60d7-4790-a150-de3fcd38687c.jpeg",
                "윈터", R.drawable.img_user_default, "그룹6", "파스타 맛집 다녀온 날", "건대 김밥 장한평점",
                true, 34, 1, "2021.06.11"),
            PostData("https://tttimagebucket.s3.ap-northeast-2.amazonaws.com/board/31dbde96-60d7-4790-a150-de3fcd38687c.jpeg",
                "한소희", R.drawable.img_user_default, "그룹7", "오코노미야끼 맛집 다녀온 날", "잠실 김밥 장한평점",
                true, 4, 122, "2024.01.17")

        )
    }
}