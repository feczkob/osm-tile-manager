package com.feczkob.osmtiles.rename

import io.github.cdimascio.dotenv.Dotenv
import java.io.File
import java.nio.file.Files

private const val TILES_PATH = "TILES_PATH"
private const val ENVIRONMENT_VARIABLE_IS_NOT_SET = "environment variable is not set"

fun main() {
    val rootDirectory = File(Dotenv.load()[TILES_PATH] ?: error("$TILES_PATH $ENVIRONMENT_VARIABLE_IS_NOT_SET"))
    renameFilesAndDirectories(rootDirectory)
    println("Directories and files renamed successfully.")
}

fun renameFilesAndDirectories(file: File) {
    if (!file.exists() || !file.isDirectory) return
    val filteredFiles = setOf(".DS_Store", "README.md")

    file.listFiles()
        ?.filter { it.name !in filteredFiles }
        ?.sortedBy { it.name }
        ?.forEachIndexed { index, f ->
            if (f.isDirectory) {
                renameFilesAndDirectories(f)
                val newDirectoryName = File(file, index.toString())
                Files.move(f.toPath(), newDirectoryName.toPath())
            } else {
                val newFileName = File(file, "$index.png")
                Files.move(f.toPath(), newFileName.toPath())
            }
        }
}
