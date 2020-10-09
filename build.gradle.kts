plugins {
    java
    application
    id("org.openjfx.javafxplugin") version "0.0.9"
    kotlin("jvm") version "1.4.10"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.openjfx:javafx-fxml:15")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

application {
    mainClass.set("com.alex.chatclient.AppInitializer")
}

javafx {
    version = "14"
    modules("javafx.controls", "javafx.fxml")
}
