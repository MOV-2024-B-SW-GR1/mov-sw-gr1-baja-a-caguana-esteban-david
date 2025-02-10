package com.example.examen02


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examen02.adapters.UpdateAdapter
import com.example.examen02.data.Actualizacion


class ActualizacionesListActivity : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var adapter: UpdateAdapter
    private val actualizaciones = mutableListOf<Actualizacion>()
    private var videoGameId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizaciones_list)

        // Obtener el ID del videojuego desde el Intent
        videoGameId = intent.getIntExtra("videoGameId", -1)

        // Inicializar base de datos
        databaseHelper = DatabaseHelper(this)

        // Configurar RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewUpdates)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UpdateAdapter(
            actualizaciones,
            onEditClick = { actualizacion -> editActualizacion(actualizacion) },
            onDeleteClick = { actualizacion -> deleteActualizacion(actualizacion) }
        )
        recyclerView.adapter = adapter

        // Configurar botón para agregar nueva actualización
        findViewById<Button>(R.id.addUpdateBtn).setOnClickListener {
            val intent = Intent(this, ActualizacionDetailsActivity::class.java)
            intent.putExtra("videoGameId", videoGameId)
            startActivity(intent)
        }
        loadActualizaciones()
    }

    private fun loadActualizaciones() {
        actualizaciones.clear()
        actualizaciones.addAll(databaseHelper.getActualizacionesByVideoJuego(videoGameId))
        adapter.notifyDataSetChanged()
    }

    private fun editActualizacion(actualizacion: Actualizacion) {
        val intent = Intent(this, ActualizacionDetailsActivity::class.java)
        intent.putExtra("versionId", actualizacion.id)  // Unificado con ActualizacionDetailsActivity
        intent.putExtra("videoGameId", actualizacion.videojuegoId)
        startActivity(intent)
    }

    private fun deleteActualizacion(actualizacion: Actualizacion) {
        databaseHelper.deleteActualizacion(actualizacion.id)
        loadActualizaciones()
    }

    override fun onResume() {
        super.onResume()
        loadActualizaciones()
    }
}
