package com.wewrite.android.util

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.Fragment

const val PERMISSION_CODE = 101

class PermissionCheck {
    companion object {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        fun requestPermissionAndOpenGallery(
            activity: FragmentActivity,
            requestPermissionLauncher: ActivityResultLauncher<String>,
            setImageResult: ActivityResultLauncher<Intent>
        ) {
            val permission = Manifest.permission.READ_MEDIA_IMAGES

            // 권한이 이미 허용되어 있는지 확인
            when {
                ContextCompat.checkSelfPermission(
                    activity,
                    permission
                ) == PackageManager.PERMISSION_GRANTED -> {
                    openGallery(activity, setImageResult)
                }

                else -> {
                    // 권한을 요청
                    requestPermissionLauncher.launch(permission)
                }
            }
        }

        fun showPermissionDeniedDialog(activity: Context) {
            AlertDialog.Builder(activity)
                .setMessage("권한이 거부되었습니다.")
                .setPositiveButton("확인") { _, _ ->
                    // 사용자가 확인 버튼을 누를 때 수행할 작업을 추가할 수 있습니다.
                }
                .show()
        }

        fun showPermissionDeniedDialog(fragment: Fragment) {
            AlertDialog.Builder(fragment.requireContext())
                .setMessage("권한이 거부되었습니다. 갤러리를 열 수 없습니다.")
                .setPositiveButton("확인") { _, _ ->
                    // 사용자가 확인 버튼을 누를 때 수행할 작업을 추가할 수 있습니다.
                }
                .show()
        }

        fun openGallery(activity: FragmentActivity, setImageResult: ActivityResultLauncher<Intent>) {
            Log.d("Gallery", "Opening gallery...")
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            setImageResult.launch(galleryIntent)
        }

        fun checkLocationPermission(context: Context): Boolean {
            return ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        }

        //activity에서 위치 권한 요청
        fun requestLocationPermission(activity: Activity, requestCode: Int) {
            requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                requestCode
            )
        }
    }
}

//@author: 이승민