package com.example.deber2.data

// Clases de datos para VideoJuego y Actualizacion
data class VideoGame(
    val id: Int,
    val titulo: String,
    val precio: Double,
    val fechaLanzamiento: String,
    val disponible: Boolean,
//    val actualizaciones: MutableList<Actualizacion> = mutableListOf()
)