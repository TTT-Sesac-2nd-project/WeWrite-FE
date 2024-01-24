package com.wewrite.android.ui.group

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wewrite.android.R
import com.wewrite.android.databinding.FragmentGroupBinding
import com.wewrite.android.databinding.RvMyGroupBinding
import com.wewrite.android.ui.home.GroupData

class GroupFragment : Fragment() {

    private lateinit var binding: FragmentGroupBinding
    private val myGroupList: List<MyGroupData> = generateDummyMyGroupData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupBinding.inflate(inflater, container, false)
        setupMyGroupRecyclerView()

        return binding.root
    }


    private fun setupMyGroupRecyclerView() {
        val recyclerViewList: RecyclerView = binding.rvMyGroup
        recyclerViewList.layoutManager = GridLayoutManager(requireContext(),
            1, GridLayoutManager.VERTICAL, false)

        val myGroupAdapter = MyGroupAdapter(myGroupList)
        Log.e("GroupFragment", "myGroupAdapter: $myGroupAdapter")
        recyclerViewList.adapter = myGroupAdapter
    }

    private fun generateDummyMyGroupData(): List<MyGroupData> {
        return listOf(
            MyGroupData(R.drawable.img_group_default, "그룹1", 3),
            MyGroupData(R.drawable.img_group_default, "그룹2", 4),
            MyGroupData(R.drawable.img_group_default, "그룹3", 5),
            MyGroupData(R.drawable.img_group_default, "그룹4", 6),
            MyGroupData(R.drawable.img_group_default, "그룹5", 7),
            MyGroupData(R.drawable.img_group_default, "그룹6", 8),
            MyGroupData(R.drawable.img_group_default, "그룹7", 9),
            MyGroupData(R.drawable.img_group_default, "그룹8", 10),
            MyGroupData(R.drawable.img_group_default, "그룹9", 11),
            MyGroupData(R.drawable.img_group_default, "그룹10", 12)
        )

    }
}