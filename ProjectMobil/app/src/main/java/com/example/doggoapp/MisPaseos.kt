package com.example.doggoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doggoapp.adapters.PaseoAdapter
import com.example.doggoapp.data.Paseo

class MisPaseos : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var adapter: PaseoAdapter
    private val paseos = mutableListOf<Paseo>()
    private var doctorId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_paseos)

        doctorId = intent.getIntExtra("doctorId", -1)
        databaseHelper = DatabaseHelper(this)

        val recyclerView: RecyclerView = findViewById(R.id.list_paseos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PaseoAdapter(paseos, { paseo -> editPaciente(paseo) }, { paseo -> deletePaciente(paseo) })
        recyclerView.adapter = adapter

        loadPacientes()

        val btn_back = findViewById<ImageButton>(R.id.btn_backPaseos)
        btn_back.setOnClickListener {
            finish()
        }
    }

    private fun loadPacientes() {
        paseos.clear()
        paseos.addAll(databaseHelper.getAllPaseos())
        adapter.notifyDataSetChanged()
    }

    private fun editPaciente(paseo: Paseo) {
        val intent = Intent(this, Reservar::class.java)
        intent.putExtra("dogWalkerId", paseo.idDogWalker)
        startActivity(intent)
    }

    private fun deletePaciente(paseo: Paseo) {
//        databaseHelper.deletePaciente(paciente.id)
        loadPacientes()
    }



    override fun onResume() {
        super.onResume()
        loadPacientes()
    }
}