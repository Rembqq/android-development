package com.example.dbapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import androidx.navigation.fragment.findNavController


class InputFragment : Fragment() {
    private var selectedFaculty: String = "ІХФ"
    private var selectedCourse: String = "1 курс"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_input, container, false)

        val facultyGroup = view.findViewById<RadioGroup>(R.id.facultyGroup)
        val courseGroup = view.findViewById<RadioGroup>(R.id.courseGroup)
        val btnConfirm = view.findViewById<Button>(R.id.btnConfirm)

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
//                R.id.radio_FSP -> "ФСП"
//                R.id.radio_FPM -> "ФПМ"
//                R.id.radio_FMF -> "ФМФ"
//                R.id.radio_HTF -> "ХТФ"
                else -> "ІХФ"
            }
        }

        courseGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedCourse = when (checkedId) {
                R.id.radio_course1 -> "1 курс"
                R.id.radio_course2 -> "2 курс"
                R.id.radio_course3 -> "3 курс"
                R.id.radio_course4 -> "4 курс"
                else -> "1 курс"
            }
        }

        btnConfirm.setOnClickListener {
            val bundle = Bundle().apply {
                putString("resultText", "Вибрано: $selectedFaculty, $selectedCourse")
            }

            // Перехід до ResultFragment із передачею даних через Bundle
//            val resultFragment = ResultFragment()
//            resultFragment.arguments = bundle
//
//            // Виконуємо перехід
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, resultFragment)
//                .commit()
            findNavController().navigate(R.id.action_inputFragment_to_resultFragment, bundle)
        }

        return view;
    }
}