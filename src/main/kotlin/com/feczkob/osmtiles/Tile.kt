package com.feczkob.osmtiles

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.PI
import kotlin.math.atan
import kotlin.math.sinh

class Tile(private val zoom: Int, private val x: Int, private val y: Int) {
    override fun toString(): String = "Tile(zoom=$zoom, x=$x, y=$y)"

    // * https://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
    fun topLeft(): Point {
        val n = 1 shl zoom
        val lonDeg = x.toDouble() / n * 360.0 - 180.0
        val latRad = atan(sinh(PI * (1 - 2 * y.toDouble() / n)))
        val latDeg = Math.toDegrees(latRad)
        return Point(latDeg, lonDeg)
    }

    fun download(path: String) {
        val fetchedData = fetch()
        if (fetchedData != null) {
            val directory = File(path)
            if (!directory.exists()) {
                directory.mkdirs()
            }

            val filePath = "$path/$zoom/$x"
            val file = File(filePath)
            if (!file.exists()) {
                file.mkdirs()
            }

            val outputStream = FileOutputStream("$filePath/$y.png")
            val bufferedOutputStream = BufferedOutputStream(outputStream)
            bufferedOutputStream.write(fetchedData.readBytes())
            bufferedOutputStream.close()
            outputStream.close()

            println("Tile saved to: $filePath/$y.png")
        } else {
            println("Failed to fetch tile.")
        }
    }

    private fun fetch() =
        try {
            val url = URL("https://tile.openstreetmap.org/$zoom/$x/$y.png")
            val connection = url.openConnection() as HttpURLConnection
            // OSM requires a user-agent
            connection.setRequestProperty("User-Agent", "Chrome/120.0.0.0 Safari/537.36")
            connection.doInput = true
            connection.connect()
            BufferedInputStream(connection.inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
}
