package com.wewrite.android.ui.group

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wewrite.android.api.model.GroupResponse
import com.wewrite.android.api.repository.GroupRepository
import com.wewrite.android.databinding.FragmentGroupBinding
import kotlinx.coroutines.launch

class GroupFragment : Fragment() {

    private lateinit var binding: FragmentGroupBinding
    private lateinit var groupRepository: GroupRepository
    private lateinit var groupList: List<GroupResponse.GroupData>
    private lateinit var joinGroupCode: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupBinding.inflate(inflater, container, false)
        goToGroupCreateActivity()
        joinGroup()
        groupRepository = GroupRepository.create()
        refreshGroupList()

        return binding.root
    }

    private fun refreshGroupList() {
        lifecycleScope.launch {
            try {
                groupList = getGroupList()
                setupMyGroupRecyclerView()

            } catch (e: Exception) {
                Log.e("GroupFragment", e.toString())
            }
        }
    }

    private fun setupMyGroupRecyclerView() {
        val recyclerViewList: RecyclerView = binding.rvMyGroup
        recyclerViewList.layoutManager = GridLayoutManager(requireContext(),
            1, GridLayoutManager.VERTICAL, false)

        val myGroupAdapter = MyGroupAdapter(groupList)
        recyclerViewList.adapter = myGroupAdapter
    }

    private suspend fun getGroupList(): List<GroupResponse.GroupData> {
        return try {
            val response = groupRepository.getGroupList()
            response.data
        } catch (e: Exception) {
            Log.e("GroupFragment", e.toString())
            emptyList()
        }
    }

    private fun goToGroupCreateActivity() {
        binding.clGroupCreate.setOnClickListener {
            Intent(requireContext(), GroupCreateActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun joinGroup() {
        binding.clGroupJoin.setOnClickListener {
            val ad = AlertDialog.Builder(requireContext())
            ad.setTitle("그룹 코드 입력")

            val et = EditText(requireContext())
            ad.setView(et)

            ad.setPositiveButton("입력") { dialog, _ ->
                joinGroupCode = et.text.toString()
                lifecycleScope.launch {
                    try {
                        communicateJoinGroup()
                        refreshGroupList()
                        dialog.dismiss()
                    } catch (e: Exception) {
                        Log.e("GroupFragment", e.toString())
                    }
                }
            }

            ad.setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }

            ad.show()
        }
    }
    private suspend fun communicateJoinGroup() {

        try {
            val response = groupRepository.joinGroup(joinGroupCode)
            when (response.code) {
                200 -> Toast.makeText(requireContext(), "그룹에 가입되었습니다", Toast.LENGTH_SHORT).show()
                409 -> Toast.makeText(requireContext(), "이미 가입한 그룹입니다", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(requireContext(), "존재하지 않는 그룹코드 입니다.", Toast.LENGTH_SHORT).show()
            };
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "존재하지 않는 그룹코드 입니다.", Toast.LENGTH_SHORT).show()
        }
    }

}

//@author: 이승민