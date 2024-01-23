package com.wewrite.android.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wewrite.android.R
import com.wewrite.android.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val groupList: List<GroupData> = generateDummyGroupData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setGroupList()

        return binding.root
    }

    private fun setGroupList() {
        val recyclerViewList: RecyclerView = binding.recyclerGroup
        recyclerViewList.layoutManager = GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)

        Log.e("groupList", groupList.toString())
        val groupAdapter = HomeGroupAdapter(groupList)
        recyclerViewList.adapter = groupAdapter
    }

    private fun generateDummyGroupData(): List<GroupData> {
        return listOf(
            GroupData(R.drawable.img_group_default, "그룹1"),
            GroupData(R.drawable.img_group_default, "그룹2"),
            GroupData(R.drawable.img_group_default, "그룹3"),
            GroupData(R.drawable.img_group_default, "그룹4"),
            GroupData(R.drawable.img_group_default, "그룹5"),
            GroupData(R.drawable.img_group_default, "그룹6"),
            GroupData(R.drawable.img_group_default, "그룹7"),
            GroupData(R.drawable.img_group_default, "그룹8"),
            GroupData(R.drawable.img_group_default, "그룹9"),
            GroupData(R.drawable.img_group_default, "그룹10")
        )
    }


}