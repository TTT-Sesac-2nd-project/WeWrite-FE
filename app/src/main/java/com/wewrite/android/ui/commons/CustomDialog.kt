package com.wewrite.android.ui.commons

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wewrite.android.databinding.DialogMoreBinding

class CustomDialog(private val context: AppCompatActivity) {

    private lateinit var binding: DialogMoreBinding
    private val dialog = Dialog(context)

    fun show(content: String) {
        binding = DialogMoreBinding.inflate(context.layoutInflater)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE)
        dialog.setContentView(binding.root)
        dialog.show()

        binding.dialogText.text = content

        binding.dialogText.setOnClickListener {
            dialog.dismiss()

            if (content == "그룹 나가기") {
                //TODO: 탈퇴 로직
                Toast.makeText(context, "그룹에서 탈퇴되었습니다.", Toast.LENGTH_SHORT).show()
            }
            else if (content == "삭제하기") {
                //TODO: 글 삭제 로직
                Toast.makeText(context, "글이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }

        }


        binding.dialogCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}