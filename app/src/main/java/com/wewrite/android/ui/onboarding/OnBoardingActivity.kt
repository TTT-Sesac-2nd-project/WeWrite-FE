// OnBoardingActivity.kt
package com.wewrite.android.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.wewrite.android.R
import com.wewrite.android.databinding.ActivityOnBoardingBinding
import com.wewrite.android.ui.MainActivity

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()
        addIndicatorDots(onBoardingList.size)
        setOnNextButton()
    }

    private fun initViewPager() {
        val viewPager = binding.onBoardingViewpager
        val onBoardingAdapter = OnBoardingAdapter(onBoardingList)
        viewPager.adapter = onBoardingAdapter
        viewPager.registerOnPageChangeCallback(onPageChangeCallback)
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
                        this@OnBoardingActivity,
                        R.drawable.ind_onboarding_false
                    )
                )
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(8, 0, 8, 0)
                }
            }
            binding.onBoardingIndicator.addView(dot)
        }
    }

    private fun updateIndicator(position: Int) {
        for (i in 0 until binding.onBoardingIndicator.childCount) {
            val dot = binding.onBoardingIndicator.getChildAt(i) as ImageView
            dot.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    if (i == position) R.drawable.ind_onboarding_true else R.drawable.ind_onboarding_false
                )
            )
        }
    }

    private fun setOnNextButton() {
        val onBoardingButton = binding.nextButton
        onBoardingButton.setOnClickListener {
            if (binding.onBoardingViewpager.currentItem < onBoardingList.size - 1) {
                binding.onBoardingViewpager.currentItem += 1
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

}
