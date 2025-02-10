package com.example.examen02


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examen02.adapters.VideoGameAdapter
import com.example.examen02.data.VideoGame


class VideoGamesList : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var adapter: VideoGameAdapter
    private val videoGames = mutableListOf<VideoGame>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_games_list)

        databaseHelper = DatabaseHelper(this)

        // Configuración del RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewVideoGames)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = VideoGameAdapter(
            videoGames,
            onEditClick = { videoGame -> openVideoGameDetails(videoGame) },
            onDeleteClick = { videoGame -> deleteVideoGame(videoGame) },
            onDetailsClick = { videoGame -> viewGameDetails(videoGame) }
        )
        recyclerView.adapter = adapter

        // Configuración del botón para añadir un nuevo videojuego
        findViewById<Button>(R.id.btnNewVideoGame).setOnClickListener {
            val intent = Intent(this, VideoGameDetailsActivity::class.java)
            startActivity(intent)
        }

        // Cargar los videojuegos desde la base de datos
        loadVideoGames()
    }

    // Cargar la lista de videojuegos
    private fun loadVideoGames() {
        videoGames.clear()
        videoGames.addAll(databaseHelper.getAllVideoJuegos())
        adapter.notifyDataSetChanged()
    }

    // Abrir los detalles del videojuego seleccionado
    private fun openVideoGameDetails(videoGame: VideoGame) {
        val intent = Intent(this, VideoGameDetailsActivity::class.java)
        intent.putExtra("videoGameId", videoGame.id)
        startActivity(intent)
    }

    // Eliminar el videojuego seleccionado
    private fun deleteVideoGame(videoGame: VideoGame) {
        databaseHelper.deleteVideoJuego(videoGame.id)
        loadVideoGames()  // Recargar los videojuegos después de la eliminación
    }

    // Ver detalles del videojuego
    private fun viewGameDetails(videoGame: VideoGame) {
        val intent = Intent(this, ActualizacionesListActivity::class.java)
        intent.putExtra("videoGameId", videoGame.id)
        startActivity(intent)
    }

    // Recargar la lista de videojuegos cada vez que la actividad se reanude
    override fun onResume() {
        super.onResume()
        loadVideoGames()
    }
}