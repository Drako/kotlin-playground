import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  java
  kotlin("jvm") version "1.3.50"
  id("org.jetbrains.kotlin.plugin.serialization") version "1.3.50"
  id("org.jetbrains.kotlin.plugin.allopen") version "1.3.50"
  id("org.jetbrains.kotlin.plugin.noarg") version "1.3.50"
}

repositories {
  mavenCentral()
  maven("https://kotlin.bintray.com/kotlinx")
}

object Version {
  const val ASSERTK = "0.20"
  const val JACKSON = "2.9.9"
  const val JUNIT = "5.5.2"
  const val KOTLINX_COROUTINES = "1.3.1"
  const val KOTLINX_SERIALIZATION = "0.9.1"
  const val MOCKITO_KOTLIN = "2.2.0"
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation(kotlin("reflect"))
  testImplementation(kotlin("test-junit5"))

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.KOTLINX_COROUTINES}")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Version.KOTLINX_SERIALIZATION}")

  testImplementation("org.junit.jupiter:junit-jupiter-api:${Version.JUNIT}")
  testImplementation("org.junit.jupiter:junit-jupiter-params:${Version.JUNIT}")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Version.JUNIT}")

  testCompile("com.willowtreeapps.assertk:assertk-jvm:${Version.ASSERTK}")
  testCompile("com.nhaarman.mockitokotlin2:mockito-kotlin:${Version.MOCKITO_KOTLIN}")

  implementation("com.fasterxml.jackson.core:jackson-core:${Version.JACKSON}")
  implementation("com.fasterxml.jackson.core:jackson-databind:${Version.JACKSON}")
  implementation("com.fasterxml.jackson.core:jackson-annotations:${Version.JACKSON}")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Version.JACKSON}")
}

project.sourceSets {
  getByName("main") {
    java.srcDirs("src/main/code")
    withConvention(KotlinSourceSet::class) {
      kotlin.srcDirs("src/main/code")
    }
  }

  getByName("test") {
    java.srcDirs("src/test/code")
    withConvention(KotlinSourceSet::class) {
      kotlin.srcDirs("src/test/code")
    }
  }
}

tasks {
  withType<Test> {
    useJUnitPlatform()
  }

  withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
  }

  "wrapper"(Wrapper::class) {
    gradleVersion = "5.6.2"
  }
}
