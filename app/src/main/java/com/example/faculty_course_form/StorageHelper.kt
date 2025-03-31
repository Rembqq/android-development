package com.example.faculty_course_form

import android.content.Context
import java.io.File

object StorageHelper {

    private const val FILE_NAME = "data_storage.txt"

    fun saveData(context: Context, data: String) {
        val file = File(context.filesDir, FILE_NAME)
        file.appendText("$data\n")  // Додає новий рядок до файлу
    }

    fun loadData(context: Context): List<String> {
        val file = File(context.filesDir, FILE_NAME)
        return if (file.exists()) {
            file.readLines()
        } else {
            emptyList()
        }
    }

}