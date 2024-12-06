package org.example
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import kotlin.random.Random
import java.time.LocalDate

data class VideoJuego(
    val id: Int,                // Entero: Identificador único
    var titulo: String,         // String: Nombre del videojuego
    var precio: Double,         // Decimal: Precio del videojuego
    var fechaLanzamiento: LocalDate, // Fecha: Fecha de lanzamiento
    var disponible: Boolean,    // Booleano: Indica si está disponible
    val actualizaciones: MutableList<Actualizacion> // Relación uno a muchos
)

// Clase Actualizacion
data class Actualizacion(
    val id: Int,                // Entero: Identificador único
    var version: String,        // String: Versión de la actualización
    var tamaño: Double,         // Decimal: Tamaño en GB
    var fechaPublicacion: LocalDate, // Fecha: Fecha de la actualización
    var esObligatoria: Boolean
)


fun printMenuOptions() {
    println("\n🌟--- Menú de Opciones ---🌟")
    println("1️⃣ Crear VideoJuego 🎮")
    println("2️⃣ Consultar VideoJuegos 🔍")
    println("3️⃣ Actualizar VideoJuego ✏️")
    println("4️⃣ Eliminar VideoJuego 🗑️")
    println("5️⃣ Crear Actualización 🆕")
    println("6️⃣ Consultar Actualizaciones 📋")
    println("7️⃣ Actualizar Actualización 🛠️")
    println("8️⃣ Eliminar Actualización ❌")
    println("9️⃣ Cerrar el Programa 🚪")
    println("\n🌟----------------------🌟\n")
    print("🔸 Seleccione una opción: ")
}

fun showMenu() {
    while (true) {
        printMenuOptions()
        val opcion = readLine()?.toIntOrNull()
        cargarVideoJuegos()
        cargarActualizaciones()
        when (opcion) {
            1 -> crearVideoJuego()
            2 -> consultarVideoJuegos()
            3 -> actualizarVideoJuego()
            4 -> eliminarVideoJuego()
            5 -> crearActualizacion()
            6 -> consultarActualizacionesDeVideoJuego()
            7 -> actualizarActualizacion()
            8 -> eliminarActualizacion()
            9 -> {
                println("👋 Cerrando el programa... ¡Hasta luego!")
                break
            }
            else -> println("⚠️ Opción no válida. Por favor, intente de nuevo.")
        }
    }
}

var videoJuegos = mutableListOf<VideoJuego>()
fun main() {
    showMenu()
}

// GAMES CRUD
fun crearVideoJuego() {
    println("\n\t🆕 Crear VideoJuego 🎮")
    print("Ingrese el nombre del VideoJuego: ")
    val nombre = readLine() ?: return println("⚠️ Nombre inválido.")
    print("Ingrese el género del VideoJuego: ")
    val genero = readLine() ?: return println("⚠️ Género inválido.")
    print("Ingrese el precio del VideoJuego: ")
    val precio = readLine()?.toDoubleOrNull() ?: return println("⚠️ Precio inválido.")

    val nuevoJuego = VideoJuego(generarIdUnico(), nombre, precio, LocalDate.now() , true,mutableListOf())
    videoJuegos.add(nuevoJuego)
    println("\n✅ VideoJuego creado exitosamente:\n $nuevoJuego")
    guardarVideoJuegos()
}

fun generarIdUnico(): Int {
    val idsExistentes = videoJuegos.map { it.id }.toSet()
    var id: Int
    do {
        id = Random.nextInt(1, 10000) // Genera un número aleatorio entre 1 y 9999
    } while (id in idsExistentes) // Asegura que el ID sea único
    return id
}


fun consultarVideoJuegos() {
    println("\n\t🔍 Lista de VideoJuegos disponibles 🎮")
    if (videoJuegos.isEmpty()) {
        println("⚠️ No hay videojuegos registrados.")
    } else {
        videoJuegos.forEach { println(it) }
    }
}


fun actualizarVideoJuego() {
    println("\n\t✏️ Actualizar VideoJuego 🎮")

    val juego = obtenerVideoJuegoPorTitulo() ?: return

    println("\n🎮 VideoJuego actual:\n $juego")

    print("Ingrese el nuevo nombre del Juego (o deje en blanco para no cambiar): ")
    val nuevoGenero = readLine()
    if (!nuevoGenero.isNullOrBlank()) juego.titulo = nuevoGenero

    print("Ingrese el nuevo precio (o deje en blanco para no cambiar): ")
    val nuevoPrecio = readLine()?.toDoubleOrNull()
    if (nuevoPrecio != null) juego.precio = nuevoPrecio

    juego.fechaLanzamiento = LocalDate.now();
    println("\n✅ VideoJuego actualizado exitosamente:\n $juego")
    guardarVideoJuegos()
}


fun eliminarVideoJuego() {
    println("\n🗑️ Eliminar VideoJuego 🎮")
    print("Ingrese el título del VideoJuego que desea actualizar: ")
    val titulo = readLine()?.trim()
    val eliminado = videoJuegos.removeIf { it.titulo.equals(titulo, ignoreCase = true) }
    if (eliminado) {
        println("✅ VideoJuego eliminado exitosamente.")
        guardarVideoJuegos()
    } else {
        println("⚠️ No se encontró el VideoJuego especificado.")
    }
}

fun obtenerVideoJuegoPorTitulo(): VideoJuego? {
    print("Ingrese el Nombre del VideoJuego: ")
    val tituloJuego = readLine()?.trim()

    return videoJuegos.find { it.titulo.equals(tituloJuego, ignoreCase = true) }.also {
        if (it == null) println("⚠️ No se encontró un VideoJuego con el título especificado.")
    }
}

// ACTUALIZACIONES CRUD

fun crearActualizacion() {
    println("\n\t🆕 Crear Actualización 🎮")
    val juego = obtenerVideoJuegoPorTitulo() ?: return
    print("Ingrese la versión de la actualización: ")
    val version = readLine() ?: return println("⚠️ Versión inválida.")

    print("Ingrese el tamaño de la actualización (GB): ")
    val tamaño = readLine()?.toDoubleOrNull() ?: return println("⚠️ Tamaño inválido.")

    print("¿Es obligatoria? (sí/no): ")
    val esObligatoria = readLine()?.trim()?.lowercase() == "sí"

    val nuevaActualizacion = Actualizacion(generarIdUnico(), version, tamaño, LocalDate.now(), esObligatoria)
    juego.actualizaciones.add(nuevaActualizacion)  // Agregamos la actualización al videojuego correspondiente
    println("\n✅ Actualización creada exitosamente para el VideoJuego '${juego.titulo}':\n $nuevaActualizacion")
    guardarActualizaciones()
}

fun consultarActualizacionesDeVideoJuego() {
    println("\n\t🔍 Consultar Actualizaciones de un VideoJuego 🎮")
    val juego = obtenerVideoJuegoPorTitulo() ?: return
    if (juego.actualizaciones.isEmpty()) {
        println("⚠️ El VideoJuego '${juego.titulo}' no tiene actualizaciones registradas.")
    } else {
        println("\nActualizaciones de ${juego.titulo}:")
        juego.actualizaciones.forEach { println(it) }
    }
}

fun actualizarActualizacion() {
    val juego = obtenerVideoJuegoPorTitulo() ?: return
    println("\nActualizaciones disponibles para ${juego.titulo}:")
    juego.actualizaciones.forEach { println(it) }
    print("\nIngrese la versión de la actualización que desea modificar: ")
    val version = readLine()?.trim()
    val actualizacion = juego.actualizaciones.find { it.version.equals(version, ignoreCase = true) }
    if (actualizacion == null) {
        println("⚠️ No se encontró una actualización con la versión especificada.")
        return
    }
    print("Ingrese el nuevo tamaño (o deje en blanco para no cambiar): ")
    val nuevoTamaño = readLine()?.toDoubleOrNull()
    if (nuevoTamaño != null) actualizacion.tamaño = nuevoTamaño
    print("¿Es obligatoria? (sí/no): ")
    val esObligatoria = readLine()?.trim()?.lowercase() == "sí"
    actualizacion.esObligatoria = esObligatoria
    actualizacion.fechaPublicacion = LocalDate.now()
    println("\n✅ Actualización modificada exitosamente:\n $actualizacion")
    guardarActualizaciones()
}


fun eliminarActualizacion() {
    val juego = obtenerVideoJuegoPorTitulo() ?: return
    println("\nActualizaciones disponibles para '${juego.titulo}':")
    juego.actualizaciones.forEach { println(it) }
    print("\nIngrese la versión de la actualización que desea eliminar: ")
    val version = readLine()?.trim()
    val eliminado = juego.actualizaciones.removeIf { it.version.equals(version, ignoreCase = true) }
    if (eliminado) {
        println("✅ Actualización eliminada exitosamente.")
        guardarActualizaciones()
    } else {
        println("⚠️ No se encontró la actualización especificada.")
    }
}

// GUARDADO EN ARCHIVOS

fun guardarVideoJuegos() {
    val file = File("videoJuegos.txt")
    val writer = FileWriter(file, false) // 'false' para sobrescribir el archivo cada vez
    videoJuegos.forEach { juego ->
        writer.write("${juego.id},${juego.titulo},${juego.precio},${juego.fechaLanzamiento},${juego.disponible}\n")
    }
    writer.close()
}

fun guardarActualizaciones() {
    val file = File("actualizaciones.txt")
    val writer = FileWriter(file, false) // 'false' para sobrescribir el archivo
    videoJuegos.forEach { juego ->
        juego.actualizaciones.forEach { actualizacion ->
            writer.write("${juego.id},${actualizacion.id},${actualizacion.version},${actualizacion.tamaño},${actualizacion.fechaPublicacion},${actualizacion.esObligatoria}\n")
        }
    }
    writer.close()
}

fun cargarActualizaciones() {
    val file = File("actualizaciones.txt")
    if (!file.exists()) {
        return
    }
    val reader = BufferedReader(FileReader(file))
    val lineas = reader.readLines()
    lineas.forEach { linea ->
        val partes = linea.split(",")
        if (partes.size == 6) {
            val idJuego = partes[0].toIntOrNull() ?: return@forEach
            val idActualizacion = partes[1].toIntOrNull() ?: return@forEach
            val version = partes[2]
            val tamaño = partes[3].toDoubleOrNull() ?: return@forEach
            val fechaPublicacion = LocalDate.parse(partes[4])
            val esObligatoria = partes[5].toBoolean()

            val actualizacion = Actualizacion(idActualizacion, version, tamaño, fechaPublicacion, esObligatoria)

            val juego = videoJuegos.find { it.id == idJuego }
            juego?.actualizaciones?.add(actualizacion)
        }
    }
    reader.close()
}

fun cargarVideoJuegos() {
    videoJuegos.clear();
    val file = File("videoJuegos.txt")
    if (!file.exists()) {
        return
    }
    val reader = BufferedReader(FileReader(file))
    val lineas = reader.readLines()
    lineas.forEach { linea ->
        val partes = linea.split(",")
        if (partes.size == 5) {
            val id = partes[0].toIntOrNull() ?: return@forEach
            val titulo = partes[1]
            val precio = partes[2].toDoubleOrNull() ?: return@forEach
            val fechaLanzamiento = LocalDate.parse(partes[3])
            val disponible = partes[4].toBoolean()

            val juego = VideoJuego(id, titulo, precio, fechaLanzamiento, disponible, mutableListOf())
            videoJuegos.add(juego)
        }
    }
    reader.close()
}

