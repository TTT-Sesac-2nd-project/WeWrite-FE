package com.wewrite.android.ui.commons

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wewrite.android.api.model.BaseResponse
import com.wewrite.android.api.repository.GroupRepository
import com.wewrite.android.databinding.DialogMoreBinding

class CustomDialog(private val context: AppCompatActivity) {

    private lateinit var binding: DialogMoreBinding
    private val dialog = Dialog(context)
    private lateinit var groupRepository: GroupRepository
    private var groupId: Long = 0
    private var leaveGroupCallback: ((Boolean) -> Unit)? = null

    fun show(content: String, callback: (Boolean) -> Unit) {
        binding = DialogMoreBinding.inflate(context.layoutInflater)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE)
        dialog.setContentView(binding.root)
        dialog.show()

        this.leaveGroupCallback = callback
        this.groupId = groupId

        binding.dialogText.text = content

        binding.dialogText.setOnClickListener {
            dialog.dismiss()
            leaveGroupCallback?.invoke(true)
        }

        binding.dialogCancel.setOnClickListener {
            dialog.dismiss()
            leaveGroupCallback?.invoke(false)
        }

        dialog.show()
    }

//    private fun leaveGroup(groupId: Long) {
//        groupRepository = GroupRepository.create()
//        // Assuming `leaveGroup` is a suspend function (using coroutines)
//        context.lifecycleScope.launch {
//            try {
//                val response = groupRepository.leaveGroup(groupId)
//                leaveGroupCallback?.invoke(true)
//                Toast.makeText(context, "그룹에서 탈퇴되었습니다.", Toast.LENGTH_SHORT).show()
//            } catch (e: Exception) {
//                // Handle the exception
//                leaveGroupCallback?.invoke(false)
//            }
//        }
//    }
}
