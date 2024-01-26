package com.wewrite.android.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wewrite.android.R
import com.wewrite.android.api.model.GroupResponse
import com.wewrite.android.databinding.RvHomeGroupBinding

class HomeGroupAdapter(
    private var listener: OnGroupClickListener,
    var groupList: List<GroupResponse.GroupData>
) : RecyclerView.Adapter<HomeGroupAdapter.ViewHolder>() {

    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvHomeGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = groupList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    inner class ViewHolder(
        private val binding: RvHomeGroupBinding,
        private val listener: OnGroupClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GroupResponse.GroupData) {
            binding.groupText.text = item.groupName
            Glide.with(binding.root.context)
                .load(item.groupImageUrl)
                .placeholder(R.drawable.img_group_default)
                .error(R.drawable.img_group_default)
                .into(binding.groupImage)

            if (adapterPosition == selectedPosition) {
                binding.groupCardLayout.setBackgroundResource(R.drawable.bg_group_true)
                binding.groupText.setTextColor(binding.root.context.getColor(R.color.SkyBlue2))
                binding.groupText.setTypeface(null, android.graphics.Typeface.BOLD)
                binding.groupText.isSelected = true
            } else {
                binding.groupCardLayout.setBackgroundResource(R.drawable.bg_group_false)
                binding.groupText.setTextColor(binding.root.context.getColor(R.color.Gray2))
                binding.groupText.setTypeface(null, android.graphics.Typeface.NORMAL)
                binding.groupText.isSelected = false
            }

            binding.root.setOnClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                notifyItemChanged(selectedPosition)
                selectedPosition = adapterPosition
                notifyItemChanged(selectedPosition)

                // 클릭 이벤트를 리스너를 통해 전달
                listener.onGroupClick(item.groupId)
            }
        }
    }
}


interface OnGroupClickListener {
    fun onGroupClick(groupId: Long)
}
