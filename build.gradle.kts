import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.2.51"
}

repositories {
  mavenCentral()
}

dependencies {
  compile(kotlin("stdlib"))
  compile(kotlin("stdlib-jdk7"))
  compile(kotlin("stdlib-jdk8"))
  compile(kotlin("reflect"))

  testCompile(kotlin("test"))
  testCompile(kotlin("test-junit5"))

  compile("org.jetbrains.kotlinx:kotlinx-coroutines-core:0.23.4")

  testCompile("org.junit.jupiter:junit-jupiter-api:5.2.0")
  testCompile("org.junit.jupiter:junit-jupiter-params:5.2.0")
  testRuntime("org.junit.jupiter:junit-jupiter-engine:5.2.0")
}

val test: Test by tasks
test.useJUnitPlatform()

tasks.withType(KotlinCompile::class.java).all {
  kotlinOptions.jvmTarget = "1.8"
}

kotlin {
  experimental.coroutines = Coroutines.ENABLE
}

