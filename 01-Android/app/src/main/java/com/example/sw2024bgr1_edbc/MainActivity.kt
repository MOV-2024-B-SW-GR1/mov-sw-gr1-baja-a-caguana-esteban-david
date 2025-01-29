package com.example.sw2024bgr1_edbc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //R: resources -> resources.layout.activity_main(file.xml on folder resources.layout)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        connect UI with logic(btn)
        val btn = findViewById<Button>(R.id.btn_lifeCycle)
        btn.setOnClickListener{
            startNewActivity(ALifeCycle::class.java)
        }

        val btn2 = findViewById<Button>(R.id.btn_list_view)
        btn2.setOnClickListener{
            startNewActivity(BListView::class.java)
        }

        val btnExplicit = findViewById<Button>(R.id.btn_explicit)

        btnExplicit.setOnClickListener {
            
        }
    }

    fun startNewActivity(clase:Class<*>){
        startActivity(Intent(this, clase))
    }
}