package com.example.v2

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker

class EventActivity : AppCompatActivity() {

    private lateinit var eventTitleEditText: EditText
    private lateinit var selectDateButton: Button
    private lateinit var saveEventButton: Button

    private var selectedDateMillis: Long = 0

    @SuppressLint("ScheduleExactAlarm")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        eventTitleEditText = findViewById(R.id.eventTitleEditText)
        selectDateButton = findViewById(R.id.selectDateButton)
        saveEventButton = findViewById(R.id.saveEventButton)

        // Логування для діагностики життєвого циклу активності
        Log.d("EventActivity", "onCreate() called")

        // Вибір дати за допомогою Material Date Picker
        selectDateButton.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Event Date")
                .build()

            datePicker.addOnPositiveButtonClickListener { selectedDate ->
                selectedDateMillis = selectedDate
                selectDateButton.text = "Selected Date: ${datePicker.headerText}"
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER")
        }

        // Збереження події
        saveEventButton.setOnClickListener {
            // Перевірка, чи обрана дата
            if (selectedDateMillis <= 0) {
                Toast.makeText(this, "Please select a valid date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Отримуємо значення з полів введення (наприклад, назва події)
            val eventTitle = eventTitleEditText.text.toString()

            // Логування для діагностики
            Log.d("EventActivity", "Event Title: $eventTitle")
            Log.d("EventActivity", "Selected Date: $selectedDateMillis")

            // Перевірка, чи активність не завершується або не знищується
            if (isFinishing || isDestroyed) {
                Log.w("EventActivity", "Activity is finishing or destroyed, not setting reminder.")
                return@setOnClickListener
            }

            // Створення Intent для нагадування
            val intent = Intent(this, EventReminderReceiver::class.java)
            intent.putExtra("eventTitle", eventTitle)

            // Створення PendingIntent
            val pendingIntent = PendingIntent.getBroadcast(
                this, 0, intent, PendingIntent.FLAG_IMMUTABLE
            )

            // Налаштування AlarmManager
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                selectedDateMillis - 60000, // Нагадування за 1 хвилину до події
                pendingIntent
            )

            // Підтвердження збереження події
            Toast.makeText(this, "Event saved and reminder set!", Toast.LENGTH_SHORT).show()
        }
    }

    // Логування для діагностики життєвого циклу активності
    override fun onStart() {
        super.onStart()
        Log.d("EventActivity", "onStart() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("EventActivity", "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("EventActivity", "onDestroy() called")
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun setReminder(eventTitle: String, eventDateMillis: Long) {
        // Перевірка, чи активність не завершується або не знищується
        if (isFinishing || isDestroyed) {
            Log.w("EventActivity", "Activity is finishing or destroyed, not setting reminder.")
            return
        }

        val intent = Intent(this, EventReminderReceiver::class.java)
        intent.putExtra("eventTitle", eventTitle)

        val pendingIntent = PendingIntent.getBroadcast(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            eventDateMillis - 60000, // Нагадування за 1 хвилину до події
            pendingIntent
        )
    }
}
