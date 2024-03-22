package com.feczkob.osmtiles.rename

import io.github.cdimascio.dotenv.Dotenv
import java.io.File

fun main() {
    val rootDirectory = File(Dotenv.load()["TILES_PATH"] ?: error("TILES_PATH environment variable is not set"))
    renameFilesAndDirectories(rootDirectory)
    println("Directories and files renamed successfully.")
}

fun renameFilesAndDirectories(file: File) {
    if (!file.exists() || !file.isDirectory) return

    file.listFiles()?.filter { it.name != ".DS_Store" }
        ?.sortedBy { it.name }
        ?.forEachIndexed { index, f ->
            if (f.isDirectory) {
                renameFilesAndDirectories(f)
                val newDirectoryName = File(file, index.toString())
                f.renameTo(newDirectoryName)
            } else {
                val newFileName = File(file, "$index.png")
                f.renameTo(newFileName)
            }
        }
}
