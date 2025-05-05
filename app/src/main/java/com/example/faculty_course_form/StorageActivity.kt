package com.example.faculty_course_form

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class StorageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)

        val textViewData = findViewById<TextView>(R.id.textViewData)
        val btnClear = findViewById<Button>(R.id.btnClear)
        val btnBack = findViewById<Button>(R.id.btnBack)

        val storedData = StorageHelper.loadData(this)

        if(storedData.isEmpty()) {
            textViewData.text = "Немає збережених даних"
        } else {
            textViewData.text = storedData
                .filter { it.isNotBlank() } // прибирає порожні рядки
                .joinToString("\n")
        }

        btnClear.setOnClickListener {
            StorageHelper.clearData(this)
            textViewData.text = "Немає збережених даних"
            Toast.makeText(this, "Дані очищено!", Toast.LENGTH_SHORT).show()
        }

        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}