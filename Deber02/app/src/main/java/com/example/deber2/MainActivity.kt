package com.example.deber2

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val btn = findViewById<MaterialButton>(R.id.btnIrGames)
        btn.setOnClickListener{
            startActivity(Intent(this, VideoGamesList::class.java))
        }
    }
}