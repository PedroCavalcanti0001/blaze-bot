import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies{
        classpath("org.jetbrains.kotlin:kotlin-noarg:1.6.21")
    }
}

plugins {
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    jacoco
    application
}

jacoco { toolVersion = "0.8.8" }

subprojects {
    apply { plugin("org.springframework.boot") }
    apply { plugin("io.spring.dependency-management") }
    apply { plugin("org.jetbrains.kotlin.jvm") }
}

allprojects {
    group = "me.pedroeugenio.blazebot"
    version = "0.0.1-SNAPSHOT"
    java.sourceCompatibility = JavaVersion.VERSION_11

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.springframework.cloud:spring-cloud-starter-openfeign:3.1.5")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    tasks.bootJar { enabled = false }

    tasks.jar { enabled = true }

    task("hello").doLast {
        println("Im ${project.name}")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}