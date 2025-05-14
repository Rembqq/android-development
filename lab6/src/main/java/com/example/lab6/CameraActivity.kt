package com.example.lab6

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.lab6.databinding.ActivityCameraBinding

class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding
    private lateinit var filterProcessor: FilterProcessor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (allPermissionsGranted()) {
            setupFilter()
        } else {
            requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
        }

        binding.captureButton.setOnClickListener {
            filterProcessor.saveImage()
        }
    }

    private fun setupFilter() {
        val filterNames = FilterType.values().map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, filterNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.filterSpinner.adapter = adapter

        filterProcessor = FilterProcessor(this, binding.viewFinder)

        binding.filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                filterProcessor.setFilter(FilterType.values()[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.all { it.value }) {
            setupFilter()
        } else {
            Toast.makeText(this, "Потрібні дозволи", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}
