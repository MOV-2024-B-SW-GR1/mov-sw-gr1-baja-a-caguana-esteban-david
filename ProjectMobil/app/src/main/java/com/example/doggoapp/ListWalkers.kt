package com.example.doggoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doggoapp.adapters.DogWalkerAdapter
import com.example.doggoapp.data.DogWalker

class ListWalkers : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var adapter: DogWalkerAdapter
    private val dogWalkers = mutableListOf<DogWalker>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_walkers)

        databaseHelper = DatabaseHelper(this)

        val recyclerView: RecyclerView = findViewById(R.id.list_walkers)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val btnPaseosConsult: Button = findViewById(R.id.btn_consultar_paseos)
        btnPaseosConsult.setOnClickListener {
            val intent = Intent(this, MisPaseos::class.java)
            startActivity(intent)
        }

        adapter = DogWalkerAdapter(dogWalkers) { dogWalker -> scheduleWalk(dogWalker) }
        recyclerView.adapter = adapter

        loadDogWalkers()
    }

    private fun loadDogWalkers() {
        dogWalkers.clear()
        dogWalkers.addAll(databaseHelper.getAllDogWalkers())
        adapter.notifyDataSetChanged()
    }

    private fun scheduleWalk(dogWalker: DogWalker) {
        val intent = Intent(this, Reservar::class.java)
        intent.putExtra("dogWalkerId", dogWalker.idDogWalker)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        loadDogWalkers()
    }
}