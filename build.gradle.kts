plugins {
    id("java")
    id("org.openjfx.javafxplugin") version "0.0.14"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.openjfx:javafx-controls:21") // JavaFX 버전에 맞게 수정
    implementation("org.openjfx:javafx-fxml:21") // 필요하면 추가
}

tasks.test {
    useJUnitPlatform()
}

javafx {
    version = "21" // JavaFX 버전
    modules = listOf("javafx.controls", "javafx.fxml") // 필요한 JavaFX 모듈
}