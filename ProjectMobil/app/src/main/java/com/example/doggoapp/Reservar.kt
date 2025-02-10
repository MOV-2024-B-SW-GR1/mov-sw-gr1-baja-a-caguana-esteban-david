package com.example.doggoapp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.doggoapp.data.Paseo
import java.util.Calendar

class Reservar : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var dateButton: Button
    private lateinit var timeButton: Button
    private lateinit var razaEditText: EditText
    private lateinit var reservarButton: Button

    private var selectedDate: String = ""
    private var selectedTime: String = ""
    private var idDogWalker: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservar)

        databaseHelper = DatabaseHelper(this)

        dateButton = findViewById(R.id.dateButton)
        timeButton = findViewById(R.id.timeButton)
        razaEditText = findViewById(R.id.razaText)
        reservarButton = findViewById(R.id.reservarButton)

        idDogWalker = intent.getIntExtra("dogWalkerId", -1)

        dateButton.setOnClickListener { showDatePicker() }
        timeButton.setOnClickListener { showTimePicker() }
        reservarButton.setOnClickListener { reservarPaseo() }
        val btn_back = findViewById<ImageButton>(R.id.btn_back2)
        btn_back.setOnClickListener {
            finish()
        }
    }



    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this, { _, y, m, d ->
            selectedDate = "$d/${m + 1}/$y"
            dateButton.text = selectedDate
        }, year, month, day)

        datePicker.show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePicker = TimePickerDialog(this, { _, h, m ->
            selectedTime = String.format("%02d:%02d", h, m)
            timeButton.text = selectedTime
        }, hour, minute, true)

        timePicker.show()
    }

    private fun reservarPaseo() {
        val raza = razaEditText.text.toString()

        if (selectedDate.isEmpty() || selectedTime.isEmpty() || raza.isEmpty() || idDogWalker == -1) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val paseo = Paseo(selectedDate, selectedTime, raza, idDogWalker)
        databaseHelper.addPaseo(paseo)

        Toast.makeText(this, "Paseo reservado con éxito", Toast.LENGTH_SHORT).show()
        finish()
    }
}