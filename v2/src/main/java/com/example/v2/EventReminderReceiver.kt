package com.example.v2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class EventReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val eventTitle = intent.getStringExtra("eventTitle")
        if (eventTitle != null) {
            // Можна додати Notification для нагадування
            Toast.makeText(context, "Reminder: $eventTitle", Toast.LENGTH_LONG).show()
        }
    }
}
