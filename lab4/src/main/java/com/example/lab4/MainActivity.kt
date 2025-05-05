package com.example.lab4

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MainActivity : AppCompatActivity() {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchMediaType: Switch
    private lateinit var openButton: Button
    private lateinit var playButton: Button
    private lateinit var pauseButton: Button
    private lateinit var stopButton: Button
    private lateinit var videoView: VideoView
    private var mediaPlayer: MediaPlayer? = null
    private var currentUri: Uri? = null
    private var isVideo = false

    private val pickFileLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        uri?.let {
            currentUri = it
            if (isVideo) {
                videoView.setVideoURI(it)
                videoView.visibility = View.VISIBLE
            } else {
                mediaPlayer?.release()
                mediaPlayer = MediaPlayer.create(this, it)
                videoView.visibility = View.GONE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        switchMediaType = findViewById(R.id.switchMediaType)
        openButton = findViewById(R.id.openButton)
        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)
        stopButton = findViewById(R.id.stopButton)
        videoView = findViewById(R.id.videoView)

        switchMediaType.setOnCheckedChangeListener { _, isChecked ->
            isVideo = isChecked
        }

        openButton.setOnClickListener {
            val options = arrayOf("Файл з пристрою", "Файл з Інтернету")
            AlertDialog.Builder(this)
                .setTitle("Оберіть джерело")
                .setItems(options) { _, which ->
                    when (which) {
                        0 -> pickFileLauncher.launch(arrayOf("video/*", "audio/*"))
                        1 -> promptInternetUrl()
                    }
                }
                .show()
        }

        playButton.setOnClickListener {
            if (isVideo) videoView.start() else mediaPlayer?.start()
        }
        pauseButton.setOnClickListener {
            if (isVideo) videoView.pause() else mediaPlayer?.pause()
        }
        stopButton.setOnClickListener {
            if (isVideo) {
                videoView.stopPlayback()
                videoView.setVideoURI(currentUri)
            } else {
                mediaPlayer?.stop()
                mediaPlayer?.prepareAsync()
            }
        }
    }

    private fun promptInternetUrl() {
        val input = EditText(this)
        input.hint = "Вставте URL"

        AlertDialog.Builder(this)
            .setTitle("Завантажити з Інтернету")
            .setView(input)
            .setPositiveButton("Завантажити") { _, _ ->
                val url = input.text.toString()
                downloadFromInternet(url)
            }
            .setNegativeButton("Скасувати", null)
            .show()
    }

    private fun downloadFromInternet(url: String) {
        val fileName = url.substringAfterLast('/')
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("Завантаження медіа")
            .setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, fileName)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadId = manager.enqueue(request)

        Toast.makeText(this, "Завантаження...", Toast.LENGTH_SHORT).show()

        // Просте рішення: чек через кілька секунд (можна покращити BroadcastReceiver'ом)
        Thread {
            Thread.sleep(5000)
            val file = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName)
            runOnUiThread {
                currentUri = Uri.fromFile(file)
                if (isVideo) {
                    videoView.setVideoURI(currentUri)
                    videoView.visibility = View.VISIBLE
                } else {
                    mediaPlayer?.release()
                    mediaPlayer = MediaPlayer.create(this, currentUri)
                    videoView.visibility = View.GONE
                }
            }
        }.start()
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        super.onDestroy()
    }
}
