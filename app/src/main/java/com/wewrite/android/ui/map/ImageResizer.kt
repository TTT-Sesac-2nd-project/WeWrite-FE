package com.wewrite.android.ui.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory


object ImageResizer {
    fun resizeImage(
        context: Context,
        resourceId: Int,
        targetWidth: Int,
        targetHeight: Int
    ): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true

        // 이미지의 크기 정보를 읽어옴
        BitmapFactory.decodeResource(context.resources, resourceId, options)

        // 샘플링 비율 계산
        options.inSampleSize = calculateInSampleSize(options, targetWidth, targetHeight)

        // 비트맵 생성
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(context.resources, resourceId, options)
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        // 원본 이미지의 크기
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        // 샘플링 비율 계산
        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}

//@author: 이승민