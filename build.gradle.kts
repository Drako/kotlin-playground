import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.konan.target.PredefinedKonanTargets.getByName
import kotlin.jvm.java

plugins {
  java
  kotlin("jvm") version "1.3.0-rc-146"
  id("org.jetbrains.kotlin.plugin.serialization") version "1.3.0-rc-146"
}

repositories {
  mavenCentral()
  maven("https://dl.bintray.com/kotlin/kotlin-eap")
  maven("https://kotlin.bintray.com/kotlinx")
}

object Versions {
  const val JUNIT_JUPITER = "5.3.1"
  const val KOTLINX_COROUTINES = "0.30.2"
  const val KOTLINX_SERIALIZATION = "0.8.2-rc13"
}

dependencies {
  compile(kotlin("stdlib"))
  compile(kotlin("stdlib-jdk7"))
  compile(kotlin("stdlib-jdk8"))
  compile(kotlin("reflect"))

  testCompile(kotlin("test"))
  testCompile(kotlin("test-junit5"))

  compile("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLINX_COROUTINES}")
  compile("org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.KOTLINX_SERIALIZATION}")

  testCompile("org.junit.jupiter:junit-jupiter-api:${Versions.JUNIT_JUPITER}")
  testCompile("org.junit.jupiter:junit-jupiter-params:${Versions.JUNIT_JUPITER}")
  testRuntime("org.junit.jupiter:junit-jupiter-engine:${Versions.JUNIT_JUPITER}")
}

project.sourceSets {
  getByName("main") {
    java.srcDirs("src/main/code")
    withConvention(KotlinSourceSet::class) {
      kotlin.srcDirs("src/main/code")
    }
  }

  getByName("test") {
    java.srcDirs("src/main/code")
    withConvention(KotlinSourceSet::class) {
      kotlin.srcDirs("src/test/code")
    }
  }
}

val test: Test by tasks
test.useJUnitPlatform()

tasks.withType(KotlinCompile::class.java).all {
  kotlinOptions.jvmTarget = "1.8"
}
