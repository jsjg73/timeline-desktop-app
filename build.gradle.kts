plugins {
    id("java")
    id("application") // 추가
    id("org.openjfx.javafxplugin") version "0.0.14"
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