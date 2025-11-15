plugins {
  `kotlin-dsl`
  `java-gradle-plugin`
}

repositories {
  gradlePluginPortal()
  mavenCentral()
}

dependencies {
  implementation("org.jsoup:jsoup:1.21.2")
  implementation("io.unthrottled.doki.build.jvm:doki-build-source-jvm:88.0.7")
}

gradlePlugin {
  plugins {
    create("dokiBuildPlugin") {
      id = "io.unthrottled.dokibuildplugin"
      implementationClass = "io.unthrottled.DokiBuildPlugin"
    }
  }
}