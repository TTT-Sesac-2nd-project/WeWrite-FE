package com.wewrite.android.ui.group

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.wewrite.android.databinding.ActivityGroupCreateBinding
import com.wewrite.android.util.PermissionCheck

private lateinit var binding: ActivityGroupCreateBinding

class GroupCreateActivity : AppCompatActivity() {
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                PermissionCheck.openGallery(this, setImageResult)
            } else {
                // 권한이 거부된 경우
                PermissionCheck.showPermissionDeniedDialog(this)
            }
        }

    private val setImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            data?.data?.let {
                binding.ivGroupImage.setImageURI(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupCreateBinding.inflate(layoutInflater)
        setImagePlusButton()
        setContentView(binding.root)
    }

    private fun setImagePlusButton() {
        binding.clGroupImage.setOnClickListener {
            PermissionCheck.requestPermissionAndOpenGallery(
                this,
                requestPermissionLauncher,
                setImageResult
            )
        }
    }
}
