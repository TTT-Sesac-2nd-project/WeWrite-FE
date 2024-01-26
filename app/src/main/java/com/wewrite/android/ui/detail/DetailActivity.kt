package com.wewrite.android.ui.detail

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.wewrite.android.R
import com.wewrite.android.api.APIFactory
import com.wewrite.android.api.data.com.wewrite.android.ui.detail.PostImageViewPagerAdapter
import com.wewrite.android.api.service.BoardService
import com.wewrite.android.databinding.ActivityDetailBinding
import com.wewrite.android.ui.commons.CustomDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var postImageList: List<Int>
    private val token =
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhY2Nlc3MtdG9rZW4iLCJ0b2tlbiI6Ild6SHB2WS1oZzYtN1luTjlFbFBWeUw0YWM4V1ZTSlVOLTJVS1BYTHFBQUFCalRQaFpENFdwaEhKendYSnF3IiwidXNlcklkIjoiMzI5MDc4NzEwNyIsImlhdCI6MTcwNTk3MjQzNywiZXhwIjozNTA1OTcyNDM3fQ.xEwbOPdBMsS9BMVimsC9qgX9PTUjevyudE6g1YhaM6Q"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setPostImageList()
        setupViewPager()
        addIndicatorDots(postImageList.size)
        setMoreButton()

        getOneBoard()
    }

    private fun setPostImageList() {
        postImageList =
            arrayListOf(R.drawable.img_test1, R.drawable.img_test2, R.drawable.img_test3)
    }

    private fun setupViewPager() {
        val postImageAdapter = PostImageViewPagerAdapter(postImageList as ArrayList<Int>)
        binding.vpPostImages.adapter = postImageAdapter
        binding.vpPostImages.registerOnPageChangeCallback(onPageChangeCallback)
    }

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            updateIndicator(position)
        }
    }

    private fun addIndicatorDots(pageCount: Int) {
        for (i in 0 until pageCount) {
            val dot = ImageView(this).apply {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailActivity,
                        R.drawable.ind_detail_false
                    )
                )
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(8, 0, 8, 0)
                }
            }
            binding.DetailIndicator.addView(dot)
        }
    }

    private fun updateIndicator(position: Int) {
        for (i in 0 until binding.DetailIndicator.childCount) {
            val dot = binding.DetailIndicator.getChildAt(i) as ImageView
            dot.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    if (i == position) R.drawable.ind_detail_true else R.drawable.ind_detail_false
                )
            )
        }
    }

    private fun setMoreButton() {
        binding.btnMore.setOnClickListener {
            CustomDialog(this).show("삭제하기")
            deleteBoard()
        }
    }


    private fun getOneBoard() {
        val boardService = APIFactory.getInstance().create(BoardService::class.java)

//        val loginModel = LoginModel(this)
//        val token = loginModel.getToken()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val groupDTO = boardService.getOneBoard(token, 8L)
                Log.d("Get One Board", groupDTO.toString())

            } catch (e: Exception) {
                Log.d("에러 메시지", e.printStackTrace().toString())
            }
        }
    }

    // board 삭제
    private fun deleteBoard() {
        val boardService = APIFactory.getInstance().create(BoardService::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            try{
                // todo :  deleteBoard boardId 연결해야함

                // boardService.deleteBoard(token, )
            }
            catch(e: Exception)
            {

            }
        }

    }


}
