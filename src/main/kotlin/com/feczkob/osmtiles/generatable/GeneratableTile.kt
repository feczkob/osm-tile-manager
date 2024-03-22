package com.feczkob.osmtiles.generatable

import com.feczkob.osmtiles.model.Tile
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class GeneratableTile(
    private val tile: Tile,
    private val basePath: String,
) : Generatable {
    override val path = tile.printToPath(basePath)

    override fun generate() {
        val fetchedData = fetch()
        if (fetchedData != null) {
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

    override fun ensurePathExists() {
        require(File(basePath).exists()) { "Base path must exist." }
    }

    private fun fetch() =
        try {
            val url = URL("https://tile.openstreetmap.org/${tile.printToUrl()}")
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
