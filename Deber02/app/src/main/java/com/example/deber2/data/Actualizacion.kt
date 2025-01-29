package com.example.deber2.data

import java.time.LocalDate


data class Actualizacion(
    val id: Int,
    val version: String,
    val tamaño: Double,
    val fechaPublicacion: String,
    val esObligatoria: Boolean,
    val videojuegoId: Int
)