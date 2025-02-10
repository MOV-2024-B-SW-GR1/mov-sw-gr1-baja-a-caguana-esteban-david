package com.example.doggoapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.doggoapp.data.DogWalker
import com.example.doggoapp.data.Paseo

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    companion object {
        const val DATABASE_NAME = "DoggoApp.db"
        const val DATABASE_VERSION = 1

        // Tabla DogWalker
        const val TABLE_DOG_WALKER = "DogWalker"
        const val COLUMN_DOG_WALKER_ID = "idDogWalker"
        const val COLUMN_DOG_WALKER_NAME = "nombre"
        const val COLUMN_DOG_WALKER_PHONE = "numeroTelefono"
        const val COLUMN_DOG_WALKER_PHOTO = "foto"

        // Tabla Paseo
        const val TABLE_PASEO = "Paseo"
        const val COLUMN_PASEO_ID = "idPaseo"
        const val COLUMN_PASEO_DATE = "fecha"
        const val COLUMN_PASEO_TIME = "hora"
        const val COLUMN_PASEO_BREED = "raza"
        const val COLUMN_PASEO_DOG_WALKER_ID = "idDogWalker"

        // Tabla Usuario
        const val TABLE_USUARIO = "Usuario"
        const val COLUMN_USUARIO_EMAIL = "email"
        const val COLUMN_USUARIO_PASSWORD = "contrasena"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $TABLE_DOG_WALKER (" +
                    "$COLUMN_DOG_WALKER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_DOG_WALKER_NAME TEXT NOT NULL, " +
                    "$COLUMN_DOG_WALKER_PHONE TEXT NOT NULL, " +
                    "$COLUMN_DOG_WALKER_PHOTO TEXT)"
        )

        db.execSQL(
            "CREATE TABLE $TABLE_PASEO (" +
                    "$COLUMN_PASEO_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_PASEO_DATE TEXT NOT NULL, " +
                    "$COLUMN_PASEO_TIME TEXT NOT NULL, " +
                    "$COLUMN_PASEO_BREED TEXT NOT NULL, " +
                    "$COLUMN_PASEO_DOG_WALKER_ID INTEGER NOT NULL, " +
                    "FOREIGN KEY ($COLUMN_PASEO_DOG_WALKER_ID) REFERENCES $TABLE_DOG_WALKER($COLUMN_DOG_WALKER_ID))"
        )

        db.execSQL(
            "CREATE TABLE $TABLE_USUARIO (" +
                    "$COLUMN_USUARIO_EMAIL TEXT PRIMARY KEY, " +
                    "$COLUMN_USUARIO_PASSWORD TEXT NOT NULL)"
        )

        insertDefaultData(db)
    }

    private fun insertDefaultData(db: SQLiteDatabase) {
        val dogWalkers = listOf(
            DogWalker(0, "Juan Pérez", "123456789", "android.resource://com.example.doggoapp/drawable/photo_juan"),
            DogWalker(0, "Ana López", "987654321", "android.resource://com.example.doggoapp/drawable/photo_ana"),
            DogWalker(0, "Carlos Díaz", "456789123", "android.resource://com.example.doggoapp/drawable/photo_carlos"),
            DogWalker(0, "Marta González", "789123456", "android.resource://com.example.doggoapp/drawable/photo_marta"),
            DogWalker(0, "Luis Rodríguez", "321654987", "android.resource://com.example.doggoapp/drawable/photo_luis")
        )

        for (dogWalker in dogWalkers) {
            val values = ContentValues().apply {
                put(COLUMN_DOG_WALKER_NAME, dogWalker.nombre)
                put(COLUMN_DOG_WALKER_PHONE, dogWalker.numeroTelefono)
                put(COLUMN_DOG_WALKER_PHOTO, dogWalker.foto)
            }
            db.insert(TABLE_DOG_WALKER, null, values)
        }

        // Insertar usuarios por defecto
        val users = listOf(
            "INSERT INTO $TABLE_USUARIO ($COLUMN_USUARIO_EMAIL, $COLUMN_USUARIO_PASSWORD) VALUES ('admin@doggoapp.com', 'admin123')",
            "INSERT INTO $TABLE_USUARIO ($COLUMN_USUARIO_EMAIL, $COLUMN_USUARIO_PASSWORD) VALUES ('user@doggoapp.com', 'user123')"
        )

        for (query in users) {
            db.execSQL(query)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PASEO")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DOG_WALKER")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIO")
        onCreate(db)
    }

    fun addDogWalker(dogWalker: DogWalker): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DOG_WALKER_NAME, dogWalker.nombre)
            put(COLUMN_DOG_WALKER_PHONE, dogWalker.numeroTelefono)
            put(COLUMN_DOG_WALKER_PHOTO, dogWalker.foto)
        }
        val id = db.insert(TABLE_DOG_WALKER, null, values)
        db.close()
        return id
    }
    fun verifyLogin(email: String, password: String): Boolean {
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM $TABLE_USUARIO WHERE $COLUMN_USUARIO_EMAIL = ? AND $COLUMN_USUARIO_PASSWORD = ?",
            arrayOf(email, password)
        )
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }


    fun addPaseo(paseo: Paseo): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PASEO_DATE, paseo.fecha)
            put(COLUMN_PASEO_TIME, paseo.hora)
            put(COLUMN_PASEO_BREED, paseo.raza)
            put(COLUMN_PASEO_DOG_WALKER_ID, paseo.idDogWalker)
        }
        val id = db.insert(TABLE_PASEO, null, values)
        db.close()
        return id
    }

    fun getAllDogWalkers(): List<DogWalker> {
        val dogWalkers = mutableListOf<DogWalker>()
        val db = readableDatabase
        val cursor = db.query(TABLE_DOG_WALKER, null, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val dogWalker = DogWalker(
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DOG_WALKER_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOG_WALKER_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOG_WALKER_PHONE)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOG_WALKER_PHOTO))
            )
            dogWalkers.add(dogWalker)
        }
        cursor.close()
        db.close()
        return dogWalkers
    }

    fun getPaseosByDogWalkerId(dogWalkerId: Int): List<Paseo> {
        val paseos = mutableListOf<Paseo>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_PASEO,
            null,
            "$COLUMN_PASEO_DOG_WALKER_ID = ?",
            arrayOf(dogWalkerId.toString()),
            null, null, null
        )
        while (cursor.moveToNext()) {
            val paseo = Paseo(
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASEO_DATE)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASEO_TIME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASEO_BREED)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PASEO_DOG_WALKER_ID))
            )
            paseos.add(paseo)
        }
        cursor.close()
        db.close()
        return paseos
    }

    fun getAllPaseos(): List<Paseo> {
        val paseos = mutableListOf<Paseo>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_PASEO,
            null, // Selecciona todas las columnas
            null, // No hay condición WHERE (trae todos los registros)
            null,
            null, null, null
        )

        while (cursor.moveToNext()) {
            val paseo = Paseo(
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASEO_DATE)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASEO_TIME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASEO_BREED)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PASEO_DOG_WALKER_ID))
            )
            paseos.add(paseo)
        }

        cursor.close()
        db.close()
        return paseos
    }

}
