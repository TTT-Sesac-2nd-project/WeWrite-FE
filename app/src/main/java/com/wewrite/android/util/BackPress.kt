package com.wewrite.android.util

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class BackPress {
    //뒤로가기 버튼 눌렀을 시 동작
    companion object {
        fun setupBackButton(activity: AppCompatActivity) {
            activity.onBackPressedDispatcher.addCallback(activity, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.e("BackPress", "뒤로가기 버튼 눌림")
                    activity.finish()
                }
            })
        }
    }
}
//@author: 이승민