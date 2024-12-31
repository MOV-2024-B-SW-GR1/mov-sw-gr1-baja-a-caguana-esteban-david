package com.example.sw2024bgr1_edbc

class BEntrenador(
    var id: Int,
    var name: String,
    var description: String?
) {
    override fun toString(): String {
        return "$name $description"
    }
}