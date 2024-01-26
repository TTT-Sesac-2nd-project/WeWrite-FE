package com.wewrite.android.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wewrite.android.R
import com.wewrite.android.api.model.GroupResponse
import com.wewrite.android.databinding.RvHomeGroupBinding

class HomeGroupAdapter(var groupList: List<GroupResponse.GroupData>): RecyclerView.Adapter<HomeGroupAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvHomeGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = groupList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    inner class ViewHolder(private val binding: RvHomeGroupBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GroupResponse.GroupData) {
            binding.apply {
                binding.groupText.text = item.groupName
                Glide.with(root.context)
                    .load(item.groupImageUrl)
                    .placeholder(R.drawable.img_group_default)
                    .error(R.drawable.img_group_default)
                    .into(groupImage)
            }
        }
    }

}