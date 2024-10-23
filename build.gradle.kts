import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import java.util.*

plugins {
    id("java")
    id("org.springframework.boot").version("3.3.0")
    id("io.spring.dependency-management").version("1.1.5")
    id("org.hibernate.orm").version("6.5.2.Final")
    kotlin("jvm").version("1.9.20")
    kotlin("plugin.spring").version("1.9.20")
    kotlin("plugin.jpa").version("1.9.20")
//    id("org.graalvm.buildtools.native").version("0.9.20")
}

group = "cn.zjujri"
version = "0.0.8-SNAPSHOT"
java {
    setSourceCompatibility("21")
    setTargetCompatibility("21")
    toolchain{
        languageVersion = JavaLanguageVersion.of(21)
    }
}


repositories {
//    maven {
//        isAllowInsecureProtocol = true
//        url = uri("http://10.100.1.235:8081/repository/maven-public/")
//
//    }
    mavenLocal()
    mavenCentral()
}

dependencies {
//    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.5.0")
//    implementation("com.github.xiaoymin:knife4j-openapi3-spring-boot-starter:4.3.0")
    implementation("com.drewnoakes:metadata-extractor:2.19.0")
    implementation("org.flywaydb:flyway-core:10.15.2")
    runtimeOnly("com.h2database:h2:2.2.224")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}


tasks.withType<KotlinCompile>().configureEach {
//    jvmTargetValidationMode.set(org.jetbrains.kotlin.gradle.dsl.jvm.JvmTargetValidationMode.WARNING)
    kotlinOptions.jvmTarget = "21"
    kotlinOptions.freeCompilerArgs = listOf("-Xjsr305=strict")

}
tasks.withType<KotlinJvmCompile>().configureEach {
//    jvmTargetValidationMode.set(org.jetbrains.kotlin.gradle.dsl.jvm.JvmTargetValidationMode.WARNING)
    kotlinOptions.jvmTarget = "21"
    kotlinOptions.freeCompilerArgs = listOf("-Xjsr305=strict")

}

tasks.register("updateVersion") {
    val versionFileDir =
        "${projectDir.absolutePath}${File.separatorChar}src${File.separatorChar}main${File.separatorChar}java${File.separatorChar}cn${File.separatorChar}zjujri${File.separatorChar}workday${File.separatorChar}module${File.separatorChar}Version.kt"
    val oldBuildTime = oldValue(versionFileDir, "buildTime")
    val index = oldBuildTime.indexOf("=")
    val buildTime = "${oldBuildTime.substring(0, index + 1)}\"${Date()}\""
    val updateContext = File(versionFileDir).readText().replace(oldBuildTime, buildTime)
    File(versionFileDir).writeText(updateContext)
}

fun oldValue(path: String, key: String): String {
    var readString = ""
    File(path).readLines().forEach {
        if (it.contains(key)) {
            readString = it
            return readString
        }
    }
    return readString
}

tasks.test {
    useJUnitPlatform()
}
