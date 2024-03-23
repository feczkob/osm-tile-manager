package com.feczkob.osmtiles.generatable

interface Fetchable {
    val path: String

    suspend fun fetch() {
        ensurePathExists()
        generate()
    }

    suspend fun generate()

    fun ensurePathExists()
}
