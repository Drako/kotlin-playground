import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  java
  kotlin("jvm") version "1.2.51"
}

repositories {
  mavenCentral()
}

object Versions {
  const val ASSERTK = "0.10"
  const val GUICE = "4.2.0"
  const val JUNIT_JUPITER = "5.2.0"
  const val KOTLINX_COROUTINES = "0.23.4"
  const val MOCKITO_KOTLIN = "1.6.0"
}

dependencies {
  compile(kotlin("stdlib"))
  compile(kotlin("stdlib-jdk7"))
  compile(kotlin("stdlib-jdk8"))
  compile(kotlin("reflect"))

  testCompile(kotlin("test"))
  testCompile(kotlin("test-junit5"))

  testCompile("com.nhaarman:mockito-kotlin-kt1.1:${Versions.MOCKITO_KOTLIN}")

  testCompile("com.willowtreeapps.assertk:assertk:${Versions.ASSERTK}")

  compile("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLINX_COROUTINES}")

  testCompile("org.junit.jupiter:junit-jupiter-api:${Versions.JUNIT_JUPITER}")
  testCompile("org.junit.jupiter:junit-jupiter-params:${Versions.JUNIT_JUPITER}")
  testRuntime("org.junit.jupiter:junit-jupiter-engine:${Versions.JUNIT_JUPITER}")

  compile("com.google.inject:guice:${Versions.GUICE}")
}

java.sourceSets {
  "main" {
    java.srcDirs("src/main/code")
    withConvention(KotlinSourceSet::class) {
      kotlin.srcDirs("src/main/code")
    }
  }

  "test" {
    java.srcDirs("src/test/code")
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

kotlin {
  experimental.coroutines = Coroutines.ENABLE
}

