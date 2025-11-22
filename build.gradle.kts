import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.time.LocalDate
import java.time.LocalTime

plugins {
    id("java")
    id("org.springframework.boot").version("4.0.0-M3")
    id("io.spring.dependency-management").version("1.1.7")
    id("org.hibernate.orm").version("7.1.8.Final")
    kotlin("jvm").version("2.3.0-RC")
    kotlin("plugin.spring").version("2.3.0-RC")
    kotlin("plugin.jpa").version("2.3.0-RC")
//    id("org.graalvm.buildtools.native").version("0.9.20")
}

group = "cn.zjujri"
version = "0.1.2-SNAPSHOT"
java {
    setSourceCompatibility("25")
    setTargetCompatibility("25")
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}
kotlin {
    jvmToolchain(25)
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
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:3.0.0-M1")
//    implementation("com.github.xiaoymin:knife4j-openapi3-spring-boot-starter:4.3.0")
    implementation("com.drewnoakes:metadata-extractor:2.19.0")
    implementation("org.flywaydb:flyway-core:11.17.0")
    runtimeOnly("com.h2database:h2:2.4.240")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}


tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_25)
        freeCompilerArgs.addAll("-Xjsr305=strict")
        freeCompilerArgs.add("-Xreturn-value-checker=check")

    }
}

// tasks.withType<KotlinJvmCompile>().configureEach {
//     compilerOptions {
//         jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_25)
//         freeCompilerArgs.addAll("-Xjsr305=strict")
//     }
// }

tasks.register("updateVersion") {
    group = "build"
    description = "更新构建时间和版本信息"

    doFirst {
        println("🔄 开始更新构建版本信息...")
    }

    doLast {
        val versionFileDir =
            "${projectDir.absolutePath}${File.separatorChar}src${File.separatorChar}main${File.separatorChar}java${File.separatorChar}cn${File.separatorChar}zjujri${File.separatorChar}workday${File.separatorChar}module${File.separatorChar}Version.kt"
        val oldBuildTime = oldValue(versionFileDir, "BUILD_TIME")
        val index = oldBuildTime.indexOf("=")
        val buildTime = "${oldBuildTime.take(index + 1)}\"${LocalDate.now()} ${LocalTime.now()}\""
        val updateContext = File(versionFileDir).readText().replace(oldBuildTime, buildTime)
        File(versionFileDir).writeText(updateContext)
        println("✅ 构建时间已更新: ${LocalDate.now()} ${LocalTime.now()}")
    }
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
// ========== 添加的任务依赖配置 ==========
tasks.named("build") {
    dependsOn("updateVersion")
}

tasks.named("assemble") {
    dependsOn("updateVersion")
}

tasks.named("bootJar") {
    dependsOn("updateVersion")
}

tasks.named("compileKotlin") {
    dependsOn("updateVersion")
}

tasks.named("compileJava") {
    dependsOn("updateVersion")
}