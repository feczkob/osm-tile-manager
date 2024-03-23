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
}

kotlin {
    jvmToolchain(17)
}

application {
//    mainClass = "com.feczkob.osmtiles.GenerateTilesKt"
}
