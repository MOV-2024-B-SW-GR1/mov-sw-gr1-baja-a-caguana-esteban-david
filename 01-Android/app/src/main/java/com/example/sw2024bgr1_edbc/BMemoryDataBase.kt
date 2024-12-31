package com.example.sw2024bgr1_edbc

class BMemoryDataBase {
    companion object{
        val trainersArray = arrayListOf<BEntrenador>()
        init {
            trainersArray.add(BEntrenador(1, "Adrian", "aF@a.com"))
            trainersArray.add(BEntrenador(2, "Juan", "tsaF@a.com"))
            trainersArray.add(BEntrenador(3, "Mateo", "maF@a.com"))
        }
    }
}