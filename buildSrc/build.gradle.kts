import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  kotlin("jvm") version "2.3.0-Beta2"
  `kotlin-dsl`
}

kotlin {
  jvmToolchain(21)
  compilerOptions {
    jvmTarget.set(JvmTarget.JVM_21)
  }
}

repositories {
  mavenLocal()
  mavenCentral()
  gradlePluginPortal()
  maven {
    name = "GitHubPackages"
    url = uri("https://maven.pkg.github.com/doki-theme/doki-build-source-jvm")
    credentials {
      username = System.getenv("GITHUB_ACTOR") ?: ""
      password = System.getenv("GITHUB_TOKEN") ?: ""
    }
  }
}

dependencies {
  implementation("org.jsoup:jsoup:1.21.2")
  implementation("com.google.code.gson:gson:2.13.2")
  implementation("io.unthrottled.doki.build.jvm:doki-build-source-jvm:88.0.5")
}
