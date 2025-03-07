package com.example.faculty_course_form

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController


class ResultFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result, container, false)
        val resultTextView = view.findViewById<TextView>(R.id.resultText)
        val btnCancel = view.findViewById<Button>(R.id.btnCancel)

        val resultText = arguments?.getString("resultText") ?: ""
        resultTextView.text = resultText

        btnCancel.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_inputFragment)
        }

        return view
    }
}