package com.wewrite.android.ui.group

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wewrite.android.databinding.RvMyGroupBinding

class MyGroupAdapter (var myGroupList: List<MyGroupData>) : RecyclerView.Adapter<MyGroupAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvMyGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = myGroupList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return myGroupList.size
    }

    inner class ViewHolder(private val binding: RvMyGroupBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyGroupData) {
            binding.apply {
                binding.groupName.text = item.groupName
                binding.groupMemberCount.text = item.groupMemberNum.toString()
                binding.groupImage.setImageResource(item.groupImage)
            }
        }
    }
}