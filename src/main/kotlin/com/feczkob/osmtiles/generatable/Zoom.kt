package com.feczkob.osmtiles.generatable

import java.io.File

class Zoom(
    level: Int,
    private val columns: Set<Column>,
    basePath: String,
) : Generatable {
    override val path = "$basePath/$level"

    override fun generate() {
        ensurePathExists()
        columns.forEach { column ->
            column.generate()
        }
    }

    override fun ensurePathExists() {
        val directory = File(path)
        if (!directory.exists()) {
            directory.mkdirs()
        }
    }
}
