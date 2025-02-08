plugins {
    id("java")
    id("application") // 추가
    id("org.openjfx.javafxplugin") version "0.0.8"
}

application {
    mainClass.set("org.example.TaskHierarchyApp") // 실제 main 클래스 이름 입력
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.testfx:testfx-junit5:4.0.18")
    testImplementation("org.hamcrest:hamcrest:2.1")
    testImplementation("org.assertj:assertj-core:3.13.2")
}

tasks.test {
    useJUnitPlatform()
}

javafx {
    version = "21" // JavaFX 버전
    modules = listOf("javafx.controls", "javafx.fxml") // 필요한 JavaFX 모듈
}