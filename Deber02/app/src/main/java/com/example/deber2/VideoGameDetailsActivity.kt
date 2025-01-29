package com.example.deber2

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.deber2.data.VideoGame

class VideoGameDetailsActivity : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper
    private var videoGameId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_game_details)

        val tituloEditText: EditText = findViewById(R.id.etTitulo)
        val precioEditText: EditText = findViewById(R.id.etPrecio)
        val fechaLanzamientoEditText: EditText = findViewById(R.id.etFechaLanzamiento)
        val disponibleCheckBox: CheckBox = findViewById(R.id.cbDisponible)
        val saveButton: Button = findViewById(R.id.btnGuardar)

        databaseHelper = DatabaseHelper(this)
        videoGameId = intent.getIntExtra("videoGameId", -1)

        // Cargar datos del videojuego si es edición
        if (videoGameId != -1) {
            val videoGame = databaseHelper.getVideoJuegoById(videoGameId)
            videoGame?.let {
                tituloEditText.setText(it.titulo)
                precioEditText.setText(it.precio.toString())
                fechaLanzamientoEditText.setText(it.fechaLanzamiento)
                disponibleCheckBox.isChecked = it.disponible
            }
        }

        // Guardar los cambios o añadir un nuevo videojuego
        saveButton.setOnClickListener {
            val titulo = tituloEditText.text.toString()
            val precio = precioEditText.text.toString().toDoubleOrNull()
            val fechaLanzamiento = fechaLanzamientoEditText.text.toString()
            val disponible = disponibleCheckBox.isChecked

            if (titulo.isNotEmpty() && precio != null && fechaLanzamiento.isNotEmpty()) {
                val videoGame = VideoGame(videoGameId, titulo, precio, fechaLanzamiento, disponible)
                if (videoGameId == -1) {
                    databaseHelper.addVideoJuego(videoGame)
                    Toast.makeText(this, "Videojuego añadido", Toast.LENGTH_SHORT).show()
                } else {
                    databaseHelper.updateVideoJuego(videoGame)
                    Toast.makeText(this, "Videojuego actualizado", Toast.LENGTH_SHORT).show()
                }
                finish()
            } else {
                Toast.makeText(this, "Complete todos los campos correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
}