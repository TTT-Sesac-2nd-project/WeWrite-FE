package com.wewrite.android.api.data.com.wewrite.android.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wewrite.android.databinding.DetailImageSliderBinding

class PostImageViewPagerAdapter (var postImageList: ArrayList<Int>) : RecyclerView.Adapter<PostImageViewPagerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DetailImageSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = postImageList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return postImageList.size
    }

    inner class ViewHolder(private val binding: DetailImageSliderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Int) {
            binding.apply {
                binding.svDetail.setImageResource(item)
            }
        }
    }
}