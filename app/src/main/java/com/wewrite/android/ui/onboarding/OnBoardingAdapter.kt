package com.wewrite.android.ui.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wewrite.android.databinding.FragmentOnBoardingBinding

class OnBoardingAdapter(private val onBoardingList: List<OnBoardingData>) :
    RecyclerView.Adapter<OnBoardingAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: FragmentOnBoardingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(onBoardingData: OnBoardingData) {
            binding.apply {
                imageView.setImageResource(onBoardingData.image)
                onBoardingTitle.text = onBoardingData.title
                onBoardingText.text = onBoardingData.content
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            FragmentOnBoardingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = onBoardingList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return onBoardingList.size
    }
}
