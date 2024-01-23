package com.wewrite.android.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wewrite.android.databinding.RvPostBinding

class HomeGroupAdapter(var groupList: List<GroupData>): RecyclerView.Adapter<HomeGroupAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = groupList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    inner class ViewHolder(private val binding: RvPostBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GroupData) {
            binding.apply {
                binding.groupText.text = item.groupTitle
                binding.groupImage.setImageResource(item.groupImg)
            }
        }
    }

}