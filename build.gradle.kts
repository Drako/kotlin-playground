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

object Versions {
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

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLINX_COROUTINES}")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.KOTLINX_SERIALIZATION}")

  testImplementation("org.junit.jupiter:junit-jupiter-api:${Versions.JUNIT}")
  testImplementation("org.junit.jupiter:junit-jupiter-params:${Versions.JUNIT}")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Versions.JUNIT}")

  testCompile("com.willowtreeapps.assertk:assertk-jvm:${Versions.ASSERTK}")
  testCompile("com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.MOCKITO_KOTLIN}")
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
