package com.wewrite.android.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wewrite.android.R
import com.wewrite.android.api.data.com.wewrite.android.ui.detail.PostImageViewPagerAdapter
import com.wewrite.android.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
    }


    private fun setupViewPager() {

        val postImageList = arrayListOf(R.drawable.img_test1, R.drawable.img_test2, R.drawable.img_test3)
        val postImageAdapter = PostImageViewPagerAdapter(postImageList)
        binding.vpPostImages.adapter = postImageAdapter
    }
}