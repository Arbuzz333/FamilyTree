import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id ("org.springframework.boot") version "2.4.4"
    id ("io.spring.dependency-management") version "1.0.11.RELEASE"
    id ("org.liquibase.gradle") version "2.0.4"
    kotlin("jvm") version "1.4.32"
    kotlin("plugin.spring") version "1.4.32"
    kotlin("plugin.allopen") version "1.4.32"
    kotlin("plugin.jpa") version "1.4.32"
}

group = "com.avahidov"
version = "0.1.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql:42.2.14")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    // https://mvnrepository.com/artifact/javax.validation/validation-api
    implementation("javax.validation:validation-api:2.0.1.Final")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.liquibase:liquibase-core")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

tasks.withType<Test> {
    useJUnitPlatform()
}