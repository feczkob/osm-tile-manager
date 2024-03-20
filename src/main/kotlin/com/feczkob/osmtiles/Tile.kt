package com.feczkob.osmtiles

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.PI
import kotlin.math.atan
import kotlin.math.sinh

class Tile(
    private val zoom: Int,
    val x: Int,
    val y: Int,
) {
    // * https://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
    fun topLeft(): Point {
        val n = 1 shl zoom
        val lonDeg = x.toDouble() / n * 360.0 - 180.0
        val latRad = atan(sinh(PI * (1 - 2 * y.toDouble() / n)))
        val latDeg = Math.toDegrees(latRad)
        return Point(latDeg, lonDeg)
    }

    fun bottomRight(): Point {
        val n = 1 shl zoom
        val lonDeg = (x + 1).toDouble() / n * 360.0 - 180.0
        val latRad = atan(sinh(PI * (1 - 2 * (y + 1).toDouble() / n)))
        val latDeg = Math.toDegrees(latRad)
        return Point(latDeg, lonDeg)
    }

    fun download(basePath: String) {
        val fetchedData = fetch()
        if (fetchedData != null) {
            val path = path(basePath)
            val outputStream = FileOutputStream("$path.png")
            val bufferedOutputStream = BufferedOutputStream(outputStream)
            bufferedOutputStream.write(fetchedData.readBytes())
            bufferedOutputStream.close()
            outputStream.close()

            println("Tile saved to: $path.png")
        } else {
            println("Failed to fetch tile.")
        }
    }

    private fun path(basePath: String): String = "$basePath/$y"

    override fun toString(): String = "Tile(zoom=$zoom, x=$x, y=$y)"

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
