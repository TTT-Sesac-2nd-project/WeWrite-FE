package com.wewrite.android.ui.write

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.wewrite.android.databinding.ActivityWriteBinding
import java.util.Calendar

const val PERMISSION_CODE = 101

class WriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)

        setupDate()

        setContentView(binding.root)
    }

//    private fun openGallery() {
//        Log.d("Gallery", "Opening gallery...")
//        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//        pickImageLauncher.launch(gallery)
//    }

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

}