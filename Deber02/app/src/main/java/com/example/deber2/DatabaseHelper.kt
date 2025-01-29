package com.example.deber2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.deber2.data.Actualizacion
import com.example.deber2.data.VideoGame
import java.time.LocalDate

// DatabaseHelper para gestionar la base de datos
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "VideoJuegoApp.db"
        const val DATABASE_VERSION = 1

        // Tabla VideoJuego
        const val TABLE_VIDEOJUEGO = "VideoJuego"
        const val COLUMN_VIDEOJUEGO_ID = "id"
        const val COLUMN_VIDEOJUEGO_TITULO = "titulo"
        const val COLUMN_VIDEOJUEGO_PRECIO = "precio"
        const val COLUMN_VIDEOJUEGO_FECHA_LANZAMIENTO = "fecha_lanzamiento"
        const val COLUMN_VIDEOJUEGO_DISPONIBLE = "disponible"

        // Tabla Actualizacion
        const val TABLE_ACTUALIZACION = "Actualizacion"
        const val COLUMN_ACTUALIZACION_ID = "id"
        const val COLUMN_ACTUALIZACION_VERSION = "version"
        const val COLUMN_ACTUALIZACION_TAMAÑO = "tamaño"
        const val COLUMN_ACTUALIZACION_FECHA_PUBLICACION = "fecha_publicacion"
        const val COLUMN_ACTUALIZACION_ES_OBLIGATORIA = "es_obligatoria"
        const val COLUMN_ACTUALIZACION_VIDEOJUEGO_ID = "videojuego_id"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Crear tabla VideoJuego
        db.execSQL(
            "CREATE TABLE $TABLE_VIDEOJUEGO (" +
                    "$COLUMN_VIDEOJUEGO_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_VIDEOJUEGO_TITULO TEXT NOT NULL, " +
                    "$COLUMN_VIDEOJUEGO_PRECIO REAL NOT NULL, " +
                    "$COLUMN_VIDEOJUEGO_FECHA_LANZAMIENTO TEXT NOT NULL, " +
                    "$COLUMN_VIDEOJUEGO_DISPONIBLE INTEGER NOT NULL)"
        )

        // Crear tabla Actualizacion
        db.execSQL(
            "CREATE TABLE $TABLE_ACTUALIZACION (" +
                    "$COLUMN_ACTUALIZACION_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_ACTUALIZACION_VERSION TEXT NOT NULL, " +
                    "$COLUMN_ACTUALIZACION_TAMAÑO REAL NOT NULL, " +
                    "$COLUMN_ACTUALIZACION_FECHA_PUBLICACION TEXT NOT NULL, " +
                    "$COLUMN_ACTUALIZACION_ES_OBLIGATORIA INTEGER NOT NULL, " +
                    "$COLUMN_ACTUALIZACION_VIDEOJUEGO_ID INTEGER NOT NULL, " +
                    "FOREIGN KEY ($COLUMN_ACTUALIZACION_VIDEOJUEGO_ID) REFERENCES $TABLE_VIDEOJUEGO($COLUMN_VIDEOJUEGO_ID))"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ACTUALIZACION")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_VIDEOJUEGO")
        onCreate(db)
    }

    // Métodos CRUD para VideoJuego
    fun addVideoJuego(videoJuego: VideoGame): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_VIDEOJUEGO_TITULO, videoJuego.titulo)
            put(COLUMN_VIDEOJUEGO_PRECIO, videoJuego.precio)
            put(COLUMN_VIDEOJUEGO_FECHA_LANZAMIENTO, videoJuego.fechaLanzamiento.toString())
            put(COLUMN_VIDEOJUEGO_DISPONIBLE, if (videoJuego.disponible) 1 else 0)
        }
        val id = db.insert(TABLE_VIDEOJUEGO, null, values)
        db.close()
        return id
    }

    fun updateVideoJuego(videoJuego: VideoGame): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_VIDEOJUEGO_TITULO, videoJuego.titulo)
            put(COLUMN_VIDEOJUEGO_PRECIO, videoJuego.precio)
            put(COLUMN_VIDEOJUEGO_FECHA_LANZAMIENTO, videoJuego.fechaLanzamiento.toString())
            put(COLUMN_VIDEOJUEGO_DISPONIBLE, if (videoJuego.disponible) 1 else 0)
        }
        val rows = db.update(TABLE_VIDEOJUEGO, values, "$COLUMN_VIDEOJUEGO_ID = ?", arrayOf(videoJuego.id.toString()))
        db.close()
        return rows
    }

    fun deleteVideoJuego(videoJuegoId: Int): Int {
        val db = writableDatabase
        val rows = db.delete(TABLE_VIDEOJUEGO, "$COLUMN_VIDEOJUEGO_ID = ?", arrayOf(videoJuegoId.toString()))
        db.close()
        return rows
    }

    fun getAllVideoJuegos(): List<VideoGame> {
        val videoJuegos = mutableListOf<VideoGame>()
        val db = readableDatabase
        val cursor = db.query(TABLE_VIDEOJUEGO, null, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val videoJuego = VideoGame(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getDouble(2),
                cursor.getString(3),
                cursor.getInt(4) == 1
            )
            videoJuegos.add(videoJuego)
        }
        cursor.close()
        db.close()
        return videoJuegos
    }

    fun getVideoJuegoById(videoJuegoId: Int): VideoGame? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_VIDEOJUEGO,
            arrayOf(COLUMN_VIDEOJUEGO_ID, COLUMN_VIDEOJUEGO_TITULO, COLUMN_VIDEOJUEGO_PRECIO, COLUMN_VIDEOJUEGO_FECHA_LANZAMIENTO, COLUMN_VIDEOJUEGO_DISPONIBLE),
            "$COLUMN_VIDEOJUEGO_ID = ?",
            arrayOf(videoJuegoId.toString()),
            null, null, null
        )

        val videoJuego = if (cursor.moveToFirst()) {
            VideoGame(
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VIDEOJUEGO_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VIDEOJUEGO_TITULO)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_VIDEOJUEGO_PRECIO)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VIDEOJUEGO_FECHA_LANZAMIENTO)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VIDEOJUEGO_DISPONIBLE)) == 1
            )
        } else null

        cursor.close()
        db.close()
        return videoJuego
    }


    // Métodos CRUD para Actualizacion
    fun addActualizacion(actualizacion: Actualizacion): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ACTUALIZACION_VERSION, actualizacion.version)
            put(COLUMN_ACTUALIZACION_TAMAÑO, actualizacion.tamaño)
            put(COLUMN_ACTUALIZACION_FECHA_PUBLICACION, actualizacion.fechaPublicacion.toString())
            put(COLUMN_ACTUALIZACION_ES_OBLIGATORIA, if (actualizacion.esObligatoria) 1 else 0)
            put(COLUMN_ACTUALIZACION_VIDEOJUEGO_ID, actualizacion.videojuegoId)
        }
        val id = db.insert(TABLE_ACTUALIZACION, null, values)
        db.close()
        return id
    }

    fun getActualizacionesByVideoJuego(videoJuegoId: Int): List<Actualizacion> {
        val actualizaciones = mutableListOf<Actualizacion>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_ACTUALIZACION,
            arrayOf(
                COLUMN_ACTUALIZACION_ID,
                COLUMN_ACTUALIZACION_VERSION,
                COLUMN_ACTUALIZACION_TAMAÑO,
                COLUMN_ACTUALIZACION_FECHA_PUBLICACION,
                COLUMN_ACTUALIZACION_ES_OBLIGATORIA,
                COLUMN_ACTUALIZACION_VIDEOJUEGO_ID
            ),
            "$COLUMN_ACTUALIZACION_VIDEOJUEGO_ID = ?",
            arrayOf(videoJuegoId.toString()),
            null, null, null
        )

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ACTUALIZACION_ID))
                val version = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ACTUALIZACION_VERSION))
                val tamaño = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_ACTUALIZACION_TAMAÑO))
                val fechaPublicacion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ACTUALIZACION_FECHA_PUBLICACION))
                val esObligatoria = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ACTUALIZACION_ES_OBLIGATORIA)) == 1
                val videojuegoId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ACTUALIZACION_VIDEOJUEGO_ID))

                actualizaciones.add(Actualizacion(id, version, tamaño, fechaPublicacion, esObligatoria, videojuegoId))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return actualizaciones
    }

    // Método para actualizar una actualización
    fun updateActualizacion(actualizacion: Actualizacion): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ACTUALIZACION_VERSION, actualizacion.version)
            put(COLUMN_ACTUALIZACION_TAMAÑO, actualizacion.tamaño)
            put(COLUMN_ACTUALIZACION_FECHA_PUBLICACION, actualizacion.fechaPublicacion.toString())
            put(COLUMN_ACTUALIZACION_ES_OBLIGATORIA, if (actualizacion.esObligatoria) 1 else 0)
            put(COLUMN_ACTUALIZACION_VIDEOJUEGO_ID, actualizacion.videojuegoId)
        }
        val rows = db.update(
            TABLE_ACTUALIZACION, values,
            "$COLUMN_ACTUALIZACION_ID = ?", arrayOf(actualizacion.id.toString())
        )
        db.close()
        return rows
    }

    // Método para eliminar una actualización por ID
    fun deleteActualizacion(actualizacionId: Int): Int {
        val db = writableDatabase
        val rows = db.delete(
            TABLE_ACTUALIZACION, "$COLUMN_ACTUALIZACION_ID = ?",
            arrayOf(actualizacionId.toString())
        )
        db.close()
        return rows
    }

    // Método para obtener una actualización por ID
    fun getActualizacionById(actualizacionId: Int): Actualizacion? {
        val db = readableDatabase
        var actualizacion: Actualizacion? = null

        val cursor = db.query(
            TABLE_ACTUALIZACION,
            arrayOf(
                COLUMN_ACTUALIZACION_ID, COLUMN_ACTUALIZACION_VERSION, COLUMN_ACTUALIZACION_TAMAÑO,
                COLUMN_ACTUALIZACION_FECHA_PUBLICACION, COLUMN_ACTUALIZACION_ES_OBLIGATORIA, COLUMN_ACTUALIZACION_VIDEOJUEGO_ID
            ),
            "$COLUMN_ACTUALIZACION_ID = ?",
            arrayOf(actualizacionId.toString()),
            null, null, null
        )

        cursor.use {
            if (it.moveToFirst()) {
                actualizacion = Actualizacion(
                    it.getInt(it.getColumnIndexOrThrow(COLUMN_ACTUALIZACION_ID)),
                    it.getString(it.getColumnIndexOrThrow(COLUMN_ACTUALIZACION_VERSION)),
                    it.getDouble(it.getColumnIndexOrThrow(COLUMN_ACTUALIZACION_TAMAÑO)),
                    runCatching {
                        LocalDate.parse(it.getString(it.getColumnIndexOrThrow(COLUMN_ACTUALIZACION_FECHA_PUBLICACION)))
                    }.getOrNull()?.toString() ?: "",
                    it.getInt(it.getColumnIndexOrThrow(COLUMN_ACTUALIZACION_ES_OBLIGATORIA)) == 1,
                    it.getInt(it.getColumnIndexOrThrow(COLUMN_ACTUALIZACION_VIDEOJUEGO_ID))
                )
            }
        }

        db.close()
        return actualizacion
    }


}
