package com.example.lab6

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.widget.Toast
import jp.co.cyberagent.android.gpuimage.GPUImageView
import jp.co.cyberagent.android.gpuimage.filter.*
import java.io.File
import java.io.FileOutputStream

class FilterProcessor(private val context: Context, private val gpuImageView: GPUImageView) {

    fun setFilter(type: FilterType) {
        val filter = when (type) {
            FilterType.GRAYSCALE -> GPUImageGrayscaleFilter()
            FilterType.SEPIA -> GPUImageSepiaToneFilter()
            FilterType.INVERT -> GPUImageColorInvertFilter()
            else -> GPUImageFilter()
        }
        gpuImageView.filter = filter
    }

    fun saveImage() {
        val bitmap = gpuImageView.capture()
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "filtered_${System.currentTimeMillis()}.jpg")
        FileOutputStream(file).use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, it)
        }
        Toast.makeText(context, "Фото збережено: ${file.name}", Toast.LENGTH_SHORT).show()
    }
}