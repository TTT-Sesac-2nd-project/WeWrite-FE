package com.wewrite.android.ui.group

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.wewrite.android.api.repository.GroupRepository
import com.wewrite.android.databinding.ActivityGroupCreateBinding
import com.wewrite.android.util.ImageConvert
import com.wewrite.android.util.PermissionCheck
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class GroupCreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGroupCreateBinding
    private lateinit var groupRepository: GroupRepository
    private var groupImage: MultipartBody.Part? = null

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                PermissionCheck.openGallery(this, setImageResult)
            } else {
                // 권한이 거부된 경우
                PermissionCheck.showPermissionDeniedDialog(this)
            }
        }

    private val setImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                data?.data?.let { imageUri ->
                    // 이미지 URI를 ImageView에 설정
                    binding.ivGroupImage.setImageURI(imageUri)

                    // 이미지를 MultipartBody.Part로 변환
                    lifecycleScope.launch {
                        try {
                            groupImage = ImageConvert.createImagePartFromUri(this@GroupCreateActivity, imageUri)!!
                            // groupImage를 사용하여 다른 작업 수행
                        } catch (e: Exception) {
                            Log.e("YourActivity", "Error during image conversion", e)
                        }
                    }
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupCreateBinding.inflate(layoutInflater)

        binding.ivToolbarBack.setOnClickListener {
            finish()
        }
        setImagePlusButton()
        setCompleteButton()
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

    private fun setCompleteButton() {
        binding.btnComplete.setOnClickListener {
            groupRepository = GroupRepository.create()

            lifecycleScope.launch {
                try {
                    val groupName = binding.addEventEnter.text.toString()
                    if (groupImage != null) {
                        groupRepository.createGroup(groupName, groupImage)
                    } else {
                        groupRepository.createGroup(groupName, null)
                    }
                    finish()
                } catch (e: Exception) {
                    Log.e("GroupCreateActivity", e.toString())
                }
            }
        }
    }

}
