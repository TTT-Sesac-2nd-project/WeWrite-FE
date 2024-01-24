package com.wewrite.android.ui.group

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wewrite.android.R
import com.wewrite.android.databinding.ActivityGroupCreateBinding

private lateinit var binding: ActivityGroupCreateBinding

class GroupCreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}