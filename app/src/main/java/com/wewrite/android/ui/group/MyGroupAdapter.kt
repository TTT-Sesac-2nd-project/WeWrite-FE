package com.wewrite.android.ui.group

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wewrite.android.R
import com.wewrite.android.api.model.GroupResponse
import com.wewrite.android.databinding.RvMyGroupBinding

class MyGroupAdapter (private var myGroupList: List<GroupResponse.GroupData>) : RecyclerView.Adapter<MyGroupAdapter.ViewHolder>() {

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
        fun bind(item: GroupResponse.GroupData) {
            binding.apply {
                binding.groupName.text = item.groupName
                binding.groupMemberCount.text = item.groupMemberCount.toString()
                Glide.with(binding.root.context)
                    .load(item.groupImageUrl)
                    .placeholder(R.drawable.img_group_default)
                    .error(R.drawable.img_group_default)
                    .into(binding.groupImage)

                goToGroupPage(item)
            }
        }

        private fun goToGroupPage(item: GroupResponse.GroupData) {
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, GroupPageActivity::class.java)
                intent.putExtra("groupId", item.groupId)
                binding.root.context.startActivity(intent)
            }
        }
    }
}

//@author: 이승민