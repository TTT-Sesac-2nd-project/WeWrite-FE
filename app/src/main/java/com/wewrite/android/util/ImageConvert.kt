package com.wewrite.android.util

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import android.net.Uri
import android.provider.MediaStore


class ImageConvert {
    companion object {
        // 이미지를 파일로 저장하는 함수
        private fun saveBitmapToFile(bitmap: Bitmap, file: File) {
            try {
                val fos = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                fos.close()
            } catch (e: Exception) {
                Log.e("ImageConvert", "Failed to save bitmap to file", e)
            }
        }

        // Glide를 사용하여 이미지를 파일로 저장하고, 그 파일을 MultipartBody.Part로 변환
        suspend fun createImagePartFromUri(context: Context, imageUri: Uri): MultipartBody.Part? {
            return withContext(Dispatchers.IO) {
                try {
                    // 이미지 URI를 Bitmap으로 변환
                    val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)

                    // Bitmap을 파일로 저장
                    val file = createTempImageFile(context)
                    saveBitmapToFile(bitmap, file)

                    // 파일을 MultipartBody.Part로 변환
                    val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
                    MultipartBody.Part.createFormData("groupImage", file.name, requestFile)
                } catch (e: Exception) {
                    Log.e("ImageConvert", "Failed to convert image from URI to MultipartBody.Part", e)
                    null
                }
            }
        }

        // 임시 이미지 파일을 생성하는 함수
        private fun createTempImageFile(context: Context): File {
            return File.createTempFile("temp_image", ".jpeg", context.cacheDir)
        }
    }
}

//@author: 이승민