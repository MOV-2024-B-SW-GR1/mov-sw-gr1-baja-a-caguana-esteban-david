package com.example.examen02


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.examen02.data.Actualizacion

class ActualizacionDetailsActivity : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper
    private var versionId: Int = -1
    private var videoGameId: Int = -1

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizacion_details)

        // Referencias a los elementos de la interfaz
        val versionEditText: EditText = findViewById(R.id.etVersion)
        val sizeEditText: EditText = findViewById(R.id.etTamaño) // Corregido
        val releaseDateEditText: EditText = findViewById(R.id.etFechaPublicacion)
        val mandatoryCheckBox: CheckBox = findViewById(R.id.cbEsObligatoria)
        val saveButton: Button = findViewById(R.id.btnGuardarUpdate)

        // Inicializar la base de datos
        databaseHelper = DatabaseHelper(this)
        versionId = intent.getIntExtra("versionId", -1)
        videoGameId = intent.getIntExtra("videoGameId", -1)

        // Cargar datos de la versión si es edición
        if (versionId != -1) {
            val version = databaseHelper.getActualizacionById(versionId)
            if (version != null) {
                versionEditText.setText(version.version)
                sizeEditText.setText(version.tamaño.toString())
                releaseDateEditText.setText(version.fechaPublicacion.toString())
                mandatoryCheckBox.isChecked = version.esObligatoria
            }
        }

        // Acción para guardar cambios o añadir nueva versión
        saveButton.setOnClickListener {
            val versionName = versionEditText.text.toString().trim()
            val sizeText = sizeEditText.text.toString().trim()
            val size = sizeText.toDoubleOrNull()
            val releaseDate = releaseDateEditText.text.toString().trim()
            val isMandatory = mandatoryCheckBox.isChecked

            if (versionName.isEmpty()) {
                Toast.makeText(this, "Ingrese un nombre de versión", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (sizeText.isEmpty() || size == null) {
                Toast.makeText(this, "Ingrese un tamaño válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (releaseDate.isEmpty()) {
                Toast.makeText(this, "Ingrese una fecha de publicación", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val version = Actualizacion(versionId, versionName, size, releaseDate, isMandatory, videoGameId)
            if (versionId == -1) {
                databaseHelper.addActualizacion(version)
                Toast.makeText(this, "Versión añadida", Toast.LENGTH_SHORT).show()
            } else {
                databaseHelper.updateActualizacion(version)
                Toast.makeText(this, "Versión actualizada", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }
}
