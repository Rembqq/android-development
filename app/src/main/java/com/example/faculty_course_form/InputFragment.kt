package com.example.faculty_course_form

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController


class InputFragment : Fragment() {
    private var defaultFaculty: String = "ІХФ";
    private var defaultCourse: String = "1 курс";

    private var selectedFaculty: String = defaultFaculty
    private var selectedCourse: String = defaultCourse
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_input, container, false)

        val facultyGroup = view.findViewById<RadioGroup>(R.id.facultyGroup)
        val courseGroup = view.findViewById<RadioGroup>(R.id.courseGroup)
        val btnConfirm = view.findViewById<Button>(R.id.btnConfirm)
        val btnOpen = view.findViewById<Button>(R.id.btnOpen)

        facultyGroup.setOnCheckedChangeListener {_, checkedId ->
            selectedFaculty = when (checkedId) {
                R.id.radio_IXF -> "ІХФ"
                R.id.radio_PBF -> "ПБФ"
                R.id.radio_RTF -> "РТФ"
                R.id.radio_FBMI -> "ФБМІ"
                R.id.radio_FEL -> "ФЕЛ"
                R.id.radio_FIOT -> "ФІОТ"
                R.id.radio_FL -> "ФЛ"
                R.id.radio_FMM -> "ФММ"
                else -> defaultFaculty
            }
        }

        courseGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedCourse = when (checkedId) {
                R.id.radio_course1 -> "1 курс"
                R.id.radio_course2 -> "2 курс"
                R.id.radio_course3 -> "3 курс"
                R.id.radio_course4 -> "4 курс"
                else -> defaultCourse
            }
        }

        btnConfirm.setOnClickListener {

            val resultText = "Вибрано: $selectedFaculty, $selectedCourse"

            // Збереження у локальне сховище (файл)
            StorageHelper.saveData(requireContext(), resultText)

            // Відображення повідомлення про успішне збереження
            Toast.makeText(requireContext(), "Дані збережено!", Toast.LENGTH_SHORT).show()

            val bundle = Bundle().apply {
                putString("resultText", resultText)
            }

            findNavController().navigate(R.id.action_inputFragment_to_resultFragment, bundle)
        }

        btnOpen.setOnClickListener {
            val intent = Intent(activity, StorageActivity::class.java)
            startActivity(intent)
        }

        return view;
    }
}