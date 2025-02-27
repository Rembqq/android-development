package com.example.lab1_radio_buttons

import android.icu.text.CaseMap.Title
import android.os.Bundle
import android.service.autofill.OnClickAction
import android.widget.RadioGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.lab1_radio_buttons.ui.theme.Lab1_radiobuttonsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab1_radiobuttonsTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var selectedFaculty by remember {mutableStateOf("ІМ-21")}
    var selectedCourse by remember {mutableStateOf("1 курс")}
    var resultText by remember {mutableStateOf("")}

    Column (modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(32.dp))
        Row {
            RadioGroup(
                "Faculties", listOf("ІХФ", "ПБФ",
                                    "РТФ", "ФБМІ",
                                    "ФЕЛ", "ФІОТ",
                                    "ФЛ", "ФММ",
                                    "ФСП", "ФПМ",
                                    "ФМФ", "ХТФ")) {
                selectedFaculty = it;
            }
            Spacer(modifier = Modifier.width(32.dp))
            RadioGroup(
                "Courses", listOf("1 курс", "2 курс",
                                  "3 курс", "4 курс")) {
                selectedCourse = it;
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = resultText, onValueChange = {},
                  readOnly = true, modifier = Modifier.fillMaxWidth())
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { resultText = "Вибрано: $selectedFaculty, $selectedCourse"}) {
                Text("Confirm")
            }
            Button(onClick = { resultText = "" }) {
                Text("Cancel")
            }
        }
    }
}
@Composable
fun RadioGroup(title: String, options: List<String>,
               onSelectionChanged: (String) -> Unit) {
    var selectedOption by remember { mutableStateOf(options.first()) }
    Column(modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.Start) {
        Text(title, fontSize = 20.sp)
        options.forEach { option ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = {
                        selectedOption = option
                        onSelectionChanged(option)
                    }
                )
                Text(option, modifier = Modifier.padding(8.dp))
            }
        }
    }
}