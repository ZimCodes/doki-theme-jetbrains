package io.unthrottled

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

public class DokiBuildPlugin: Plugin<Project> {
  override fun apply(project: Project) {
    project.tasks.register<BuildThemesTask>("buildThemes") {
      group = "build"
      description = "Builds all Doki themes"
      buildSourceAssetDirectory.set(project.layout.projectDirectory.dir("doki-build-plugin/assets"))
      masterThemesDirectory.set(project.layout.projectDirectory.dir("masterThemes"))
      rootResourcePath.set(project.layout.projectDirectory.dir("src/main/resources"))
      resMasterThemeSchema.set(rootResourcePath.file("theme-schema/master.theme.schema.json"))
      resDokiThemesDirectory.set(rootResourcePath.dir("doki/themes"))
      resPluginXML.set(rootResourcePath.file("META-INF/plugin.xml"))
    }
    project.tasks.register<PatchHTMLTask>("patchHTML") {
      htmlDirectory.set(project.layout.projectDirectory.dir("build/html"))
    }
  }

}