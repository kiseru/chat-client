plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":repository"))
    implementation(project(":services"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
}
