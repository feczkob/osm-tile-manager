plugins {
    kotlin("jvm") version "1.9.22"
    application
}

group = "com.feczkob.osmtiles"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.cdimascio:dotenv-java:2.2.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
}

kotlin {
    jvmToolchain(17)
    sourceSets.all {
        languageSettings {
            languageVersion = "2.0"
        }
    }
}

tasks.register<JavaExec>("fetchTiles") {
    group = "tiles"
    description = "Fetches the tiles from OSM"
    mainClass = "com.feczkob.osmtiles.GenerateTilesKt"
    classpath = sourceSets.main.get().runtimeClasspath
}

tasks.register<JavaExec>("renameTiles") {
    group = "tiles"
    description = "Renames the tiles to start from 0,0"
    mainClass = "com.feczkob.osmtiles.rename.RenameTilesKt"
    classpath = sourceSets.main.get().runtimeClasspath
}
