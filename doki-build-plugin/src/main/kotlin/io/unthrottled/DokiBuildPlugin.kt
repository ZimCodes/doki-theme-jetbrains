package io.unthrottled.doki.build.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

class DokiBuildPlugin: Plugin<Project> {
  override fun apply(project: Project) {
    project.tasks.register<BuildThemesTask>("buildThemes") {
      // New theme variants must have their id include the variant name.
      variantNames.addAll("", "islands")
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
    project.tasks.register<MultiExecTask>("buildThemeDeps"){
      val install = "yarn install"
      commandExecList.set(listOf(
        "cd doki-build-source",
        install,
        "yarn build",
        "cd ../masterThemes",
        install,
        "yarn generateAllJetbrains"
      ))
      startDirectory.set(project.layout.projectDirectory)
    }
  }

}