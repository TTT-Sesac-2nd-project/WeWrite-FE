package com.wewrite.android.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.wewrite.android.R
import com.wewrite.android.api.data.com.wewrite.android.ui.detail.PostImageViewPagerAdapter
import com.wewrite.android.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var postImageList: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setPostImageList()
        setupViewPager()
        addIndicatorDots(postImageList.size)
    }

    private fun setPostImageList() {
        postImageList = arrayListOf(R.drawable.img_test1, R.drawable.img_test2, R.drawable.img_test3)
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
}
