package com.wewrite.android.ui.write

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.wewrite.android.databinding.ActivityWriteBinding
import com.wewrite.android.util.PermissionCheck
import com.wewrite.android.util.PermissionCheck.Companion.openGallery
import java.util.Calendar

const val PERMISSION_CODE = 101

class WriteActivity : AppCompatActivity() {
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openGallery(this, setImageResult)
                setImageResult
            } else {
                // 권한이 거부된 경우
                PermissionCheck.showPermissionDeniedDialog(this)
            }
        }

    private lateinit var binding: ActivityWriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)

        setupDate()

        setContentView(binding.root)
    }

    private fun showDatePickerDialog(listener: DatePickerDialog.OnDateSetListener) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)



        let {
            DatePickerDialog(this, listener, year, month, day).show()
        }
    }

    private fun setupDate() {
        binding.editDate.setOnClickListener {
            showDatePickerDialog { _, year, month, day ->
                val monthString = if (month + 1 < 10) "0${month + 1}" else "${month + 1}"
                val dayString = if (day < 10) "0$day" else "$day"

                binding.editDateText.text = "$year/$monthString/$dayString"
            }
        }
    }

    private val setImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d("Gallery", "setImageResult called")
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                data?.data?.let {
                    binding.ivPhoto.setImageURI(it)
                }
            }
        }

    private fun setImagePlusButton() {
        binding.ivPhoto.setOnClickListener {
            PermissionCheck.requestPermissionAndOpenGallery(
                this,
                requestPermissionLauncher,
                setImageResult
            )
        }
    }

}