package com.wewrite.android.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.wewrite.android.R
import com.wewrite.android.api.data.com.wewrite.android.ui.detail.PostImageViewPagerAdapter
import com.wewrite.android.api.model.BoardDetailData
import com.wewrite.android.api.repository.BookmarkRepository
import com.wewrite.android.databinding.ActivityDetailBinding
import com.wewrite.android.ui.MainActivity
import com.wewrite.android.ui.commons.CustomDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var postImageList: ArrayList<String>
    private var boardDetailData: BoardDetailData? = null
    private val boardRepository = BoardRepository.create()
    private val bookmarkRepository = BookmarkRepository.create()

    private var boardId by Delegates.notNull<Long>()
    private var isBookmarked: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        boardDetailData = intent.getSerializableExtra("boardDetailData") as? BoardDetailData
        boardId = intent.getLongExtra("boardId", 0L)
        isBookmarked = intent.getIntExtra("isBookmarked", 0)

        postImageList = (boardDetailData?.boardImageList ?: arrayListOf()) as ArrayList<String>
        setBoardData()
        setupViewPager()
        addIndicatorDots(postImageList.size)
        setMoreButton()
        setStarButton()
    }

    private fun setBoardData() {
        binding.apply {
            tvPostTitle.text = boardDetailData?.boardTitle
            tvPostContent.text = boardDetailData?.boardContent
            tvWriter.text = boardDetailData?.userName
            Glide.with(root.context)
                .load(boardDetailData?.userImage)
                .placeholder(R.drawable.img_user_default)
                .error(R.drawable.img_user_default)
                .into(ivWriter)

            btnMore.visibility = if (boardDetailData?.isWriter == 1L) ImageView.VISIBLE else ImageView.GONE
            Log.e("isWriter", isBookmarked.toString())
            btnStar.setImageResource(if (isBookmarked == 1) R.drawable.vi_star_true else R.drawable.vi_star_false)
        }
    }

    private fun setupViewPager() {
        val postImageAdapter = PostImageViewPagerAdapter(postImageList)
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
                        if (i == 0) R.drawable.ind_detail_true else R.drawable.ind_detail_false
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

    private fun deleteBoard() {
        lifecycleScope.launch(Dispatchers.Main) {
            try {
                boardRepository.deleteBoard(boardId)
                Toast.makeText(this@DetailActivity, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                navigateToMainActivity()
            } catch (e: Exception) {
                Log.e("boardResponse", e.toString())
            }
        }
    }

    private fun navigateToMainActivity() {
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
        }
        finish()
    }

    private fun setStarButton() {
        binding.btnStar.setOnClickListener {
            binding.btnStar.setImageResource(if (isBookmarked == 1) R.drawable.vi_star_true else R.drawable.vi_star_false)
            updateBookmark()
        }
    }

    private fun updateBookmark() {
        lifecycleScope.launch(Dispatchers.Main) {
            try {
                bookmarkRepository.updateBookmark(boardId)
                Log.e("bookmarkResponse", "Bookmark updated successfully.")
            } catch (e: Exception) {
                Log.e("boardResponse", e.toString())
            }
        }
    }
}

