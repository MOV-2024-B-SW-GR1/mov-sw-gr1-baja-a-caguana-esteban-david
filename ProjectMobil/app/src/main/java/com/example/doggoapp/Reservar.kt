package com.example.doggoapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Reservar : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reservar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//       adding laza spinnerr
        val optionsRazas = arrayOf("Husky", "Golden", "Pitbull", "Chihuahua")
        val razas = findViewById<Spinner>(R.id.spinner_raza)
        razas.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, optionsRazas)


        val btn = findViewById<Button>(R.id.btn_confirm2)
        btn.setOnClickListener{
            startNewActivity(Confirmacion::class.java)
        }
        val btn_back = findViewById<ImageButton>(R.id.btn_back)
        btn_back.setOnClickListener {
            finish()
        }
    }

    fun startNewActivity(clase:Class<*>){
        startActivity(Intent(this, clase))
    }
}