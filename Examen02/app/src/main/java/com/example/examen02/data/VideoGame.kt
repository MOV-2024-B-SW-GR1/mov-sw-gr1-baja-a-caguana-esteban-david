package com.example.examen02.data

// Clases de datos para VideoJuego y Actualizacion
data class VideoGame(
    val id: Int,
    val titulo: String,
    val precio: Double,
    val fechaLanzamiento: String,
    val disponible: Boolean,
    val latitude: Double? = null,
    val longitude: Double? = null
//    val actualizaciones: MutableList<Actualizacion> = mutableListOf()
)