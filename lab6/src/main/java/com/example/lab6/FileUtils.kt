package com.example.lab6

import android.content.Context
import java.io.File

object FileUtils {
    fun getOutputDirectory(context: Context): File {
        @Suppress("DEPRECATION")
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            File(it, context.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else context.filesDir
    }
}