package com.example.doggoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val signUp = findViewById<TextView>(R.id.btn_signup)
        signUp.setOnClickListener {
            startNewActivity(Reservar::class.java)
        }
        val login = findViewById<Button>(R.id.btn_login)
        login.setOnClickListener {
            startNewActivity(ListWalkers::class.java)
        }
    }

    private fun startNewActivity(clase:Class<*>){
        startActivity(Intent(this, clase))
    }
}