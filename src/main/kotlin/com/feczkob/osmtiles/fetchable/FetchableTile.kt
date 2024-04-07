package com.feczkob.osmtiles.fetchable

import com.feczkob.osmtiles.model.Tile
import kotlinx.coroutines.coroutineScope
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class FetchableTile(
    private val tile: Tile,
    private val basePath: String,
) : Fetchable() {
    override fun path() = tile.printToPath(basePath)

    override suspend fun generate() =
        coroutineScope {
            val fetchedData: ByteArray? = fetchTile()

            if (fetchedData == null) {
                printError()
                return@coroutineScope
            }

            saveTile(fetchedData)
        }

    override fun ensurePathExists() = require(File(basePath).exists()) { "Base path must exist." }

    private fun printError() = println("Failed to fetch tile.")

    private fun fetchTile(): ByteArray? =
        try {
            val url = URL("https://tile.openstreetmap.org/${tile.printToUrl()}")
            val connection = url.openConnection() as HttpURLConnection
            // OSM requires a user-agent
            connection.setRequestProperty("User-Agent", "Chrome/120.0.0.0 Safari/537.36")
            connection.doInput = true
            connection.connect()
            connection.inputStream.buffered().use { it.readBytes() }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    private fun saveTile(fetchedData: ByteArray) {
        FileOutputStream("${path()}.png").use { outputStream ->
            outputStream.buffered().use { it.write(fetchedData) }
        }
    }
}
