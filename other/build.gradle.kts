import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Date

plugins {
    id("org.springframework.boot").version("3.0.4")
    id("io.spring.dependency-management").version("1.1.0")
    id("org.jetbrains.kotlin.jvm").version("1.8.10")
    id("org.jetbrains.kotlin.plugin.spring").version("1.8.10")
//    id("org.graalvm.buildtools.native").version("0.9.20")
}

group = "cn.zjujri"
version = "0.0.6-SNAPSHOT"
java{
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    maven {
        allowInsecureProtocol = true
        url = uri("http://10.100.1.235:8081/repository/maven-public/")

    }
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.github.xiaoymin:knife4j-openapi3-jakarta-spring-boot-starter:4.0.0")
    implementation("com.drewnoakes:metadata-extractor:2.18.0")
    implementation("org.flywaydb:flyway-core:9.10.1")
    runtimeOnly("com.h2database:h2:2.1.214")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

//tasks.withType(KotlinCompile).configureEach {
//    kotlinOptions {
//        freeCompilerArgs = ["-Xjsr305=strict"]
//        jvmTarget = "17"
//    }
//}

//task updateVersion {
//    String versionFileDir = projectDir . getAbsolutePath () + File.separatorChar + "src" + File.separatorChar + "main" + File.separatorChar + "java" + File.separatorChar + "cn" + File.separatorChar + "zjujri" + File.separatorChar + "workday" + File.separatorChar + "module" + File.separatorChar + "Version.kt"
//    def oldBuildTime = oldValue (versionFileDir, "buildTime")
//    def index = oldBuildTime . indexOf ("=")
//    def buildTime = oldBuildTime . substring (0, index+1)+" ""+new Date().format("yyyy-MM-dd HH:mm:ss")+"""
//    def updateContext = new File(versionFileDir).getText("UTF-8").replaceAll(oldBuildTime, buildTime)
//    new File (versionFileDir).write(updateContext, "UTF-8")
//}

tasks.register("updateVersion") {
    var versionFileDir =
        projectDir.getAbsolutePath() + File.separatorChar + "src" + File.separatorChar + "main" + File.separatorChar + "java" + File.separatorChar + "cn" + File.separatorChar + "zjujri" + File.separatorChar + "workday" + File.separatorChar + "module" + File.separatorChar + "Version.kt"
    var oldBuildTime = oldValue(versionFileDir, "buildTime")
    var index = oldBuildTime.indexOf("=")
    var buildTime = oldBuildTime.substring(0, index + 1) + " \"" + Date() + "\""
    var updateContext = File(versionFileDir).readText().replace(oldBuildTime, buildTime)
    File(versionFileDir).writeText(updateContext)
}

fun oldValue(path:String,key:String): String {
    var readString = ""
    File(path).readLines().forEach {
        if (it.contains(key)) {
            readString = it
            return readString
        }
    }
    return readString
}

//graalvmNative {
//    binaries {
//        main {
//            buildArgs(
//                "--initialize-at-run-time=ch.qos.logback.core.util.Duration," +
//                        "ch.qos.logback.classic.Level," +
//                        "ch.qos.logback.core.util.FileSize," +
//                        "ch.qos.logback.classic.Logger," +
//                        "io.grpc.netty.shaded.io.netty.channel.MultithreadEventLoopGroup," +
//                        "io.grpc.netty.shaded.io.netty.channel.ChannelOption," +
//                        "io.grpc.netty.shaded.io.netty.util.ReferenceCountUtil," +
//                        "io.grpc.netty.shaded.io.netty.channel.AbstractChannel," +
//                        "io.grpc.netty.shaded.io.netty.util.internal.SystemPropertyUtil," +
//                        "io.grpc.netty.shaded.io.netty.channel.AbstractChannel",
//                "--trace-class-initialization=ch.qos.logback.classic.Level," +
//                        "ch.qos.logback.core.util.Duration," +
//                        "io.grpc.netty.shaded.io.netty.util.internal.SystemPropertyUtil," +
//                        "ch.qos.logback.classic.model.ConfigurationModel," +
//                        "ch.qos.logback.core.util.FileSize," +
//                        "ch.qos.logback.classic.Logger"
//            )
//            buildArgs.add("--initialize-at-run-time=ch.qos.logback.classic.Logger")
//            buildArgs.add("--initialize-at-run-time=ch.qos.logback.core.util.FileSize")
//            buildArgs.add("--initialize-at-run-time=ch.qos.logback.core.util.Duration")
//            buildArgs.add("--initialize-at-run-time=ch.qos.logback.classic.Level")
//            buildArgs.add("--initialize-at-run-time=ch.qos.logback.classic.model.ConfigurationModel")
//            buildArgs.add("--initialize-at-run-time=org.slf4j.LoggerFactory")
//            buildArgs.add("--initialize-at-run-time=ch.qos.logback.core.util.Loader")
//            buildArgs.add("--trace-object-instantiation=ch.qos.logback.classic.Logger")
//            buildArgs.add("--trace-object-instantiation=ch.qos.logback.core.util.FileSize")
//            buildArgs.add("--trace-object-instantiation=ch.qos.logback.core.util.Duration")
//            buildArgs.add("--trace-object-instantiation=ch.qos.logback.classic.Level")
//            buildArgs.add("-H:+ReportExceptionStackTraces")
//        }
//    }
//}

//tasks.named("test") {
//    useJUnitPlatform()
//}
