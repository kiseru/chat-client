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
    implementation("org.slf4j:slf4j-simple:1.7.30")

    compileOnly("org.projectlombok:lombok:1.18.16")
    annotationProcessor("org.projectlombok:lombok:1.18.16")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

application {
    mainClass.set("com.alex.chat.client.AppInitializer")
}

javafx {
    version = "14"
    modules("javafx.controls", "javafx.fxml")
}
