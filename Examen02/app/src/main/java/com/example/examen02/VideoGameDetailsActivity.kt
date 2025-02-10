package com.example.examen02

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.examen02.data.VideoGame

class VideoGameDetailsActivity : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper
    private var videoGameId: Int = -1
    private var latitude: Double? = -0.180653
    private var longitude: Double? = -78.467834

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_game_details)

        // Initialize views after setContentView()
        val tituloEditText: EditText = findViewById(R.id.etTitulo)
        val precioEditText: EditText = findViewById(R.id.etPrecio)
        val fechaLanzamientoEditText: EditText = findViewById(R.id.etFechaLanzamiento)
        val disponibleCheckBox: CheckBox = findViewById(R.id.cbDisponible)
        val selectLocationButton: Button = findViewById(R.id.btnSelectLocation)
        val saveButton: Button = findViewById(R.id.btnGuardar)
        val latitudEditText: EditText = findViewById(R.id.etLatitud)  // EditText para latitud
        val longitudEditText: EditText = findViewById(R.id.etLongitud)

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
                latitudEditText.setText(latitude.toString())  // Correctly set latitude
                longitudEditText.setText(longitude.toString())  // Correctly set longitude
            }
        }

        // Botón para abrir la actividad del mapa
        selectLocationButton.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            intent.putExtra("latitud", latitude)  // Ejemplo: Quito, Ecuador
            intent.putExtra("altitud", longitude) // Ejemplo: Quito, Ecuador
            intent.putExtra("nombre", "Ubicación seleccionada")
            startActivity(intent)
        }
        // Guardar los cambios o añadir un nuevo videojuego
        saveButton.setOnClickListener {
            val titulo = tituloEditText.text.toString()
            val precio = precioEditText.text.toString().toDoubleOrNull()
            val fechaLanzamiento = fechaLanzamientoEditText.text.toString()
            val disponible = disponibleCheckBox.isChecked
            longitude = longitudEditText.text.toString().toDoubleOrNull()
            latitude = latitudEditText.text.toString().toDoubleOrNull()

            if (titulo.isNotEmpty() && precio != null && fechaLanzamiento.isNotEmpty()) {
                val videoGame = VideoGame(videoGameId, titulo, precio, fechaLanzamiento, disponible, latitude, longitude)
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



    companion object {
        private const val REQUEST_MAP = 100
    }
}
