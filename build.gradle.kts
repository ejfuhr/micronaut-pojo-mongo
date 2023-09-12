plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.22"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.8.22"
    id("com.google.devtools.ksp") version "1.8.22-1.0.11"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.micronaut.application") version "4.0.3"
    id("io.micronaut.test-resources") version "4.0.3"
    id("io.micronaut.aot") version "4.0.3"
}

version = "0.1"
group = "com.example.micronautguide"

val kotlinVersion=project.properties.get("kotlinVersion")
var reactorAddonsVersion = "3.5.1"
var kotestVersion = "4.0.1"
var mockkVersion = "1.13.7"
repositories {
    mavenCentral()
}

dependencies {
    ksp("io.micronaut.data:micronaut-data-document-processor")
    ksp("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut.data:micronaut-data-mongodb")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.mongodb:micronaut-mongo-reactive")
    implementation("io.micronaut.reactor:micronaut-reactor")
    implementation("io.micronaut.reactor:micronaut-reactor-http-client")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    implementation ("io.projectreactor.addons:reactor-adapter:$reactorAddonsVersion")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")


    runtimeOnly("ch.qos.logback:logback-classic")


    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    runtimeOnly("org.mongodb:mongodb-driver-reactivestreams")
    runtimeOnly("org.yaml:snakeyaml")


    testAnnotationProcessor ("io.micronaut:micronaut-inject-java")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation ("io.projectreactor:reactor-test")
    testImplementation("io.micronaut:micronaut-http-client")

    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("io.micronaut.test:micronaut-test-junit5")

    testImplementation("org.junit.jupiter:junit-jupiter-engine")

//    testImplementation ("io.micronaut.test:micronaut-test-kotest5:4.0.1")
    testImplementation ("io.mockk:mockk:$mockkVersion")
//    testImplementation ("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")

    testImplementation ("io.projectreactor:reactor-test")
}


application {
    mainClass.set("com.example.micronautguide.ApplicationKt")
}
java {
    sourceCompatibility = JavaVersion.toVersion("17")
}

tasks {
    compileKotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
    compileTestKotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
}
graalvmNative.toolchainDetection.set(false)
micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.example.micronautguide.*")
    }
    aot {
    // Please review carefully the optimizations enabled below
    // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading.set(false)
        convertYamlToJava.set(false)
        precomputeOperations.set(true)
        cacheEnvironment.set(true)
        optimizeClassLoading.set(true)
        deduceEnvironment.set(true)
        optimizeNetty.set(true)
    }
}



