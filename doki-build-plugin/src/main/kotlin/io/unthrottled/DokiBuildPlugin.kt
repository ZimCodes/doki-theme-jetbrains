package io.unthrottled.doki.build.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

class DokiBuildPlugin : Plugin<Project> {
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
    project.tasks.register<MultiExecTask>("buildThemeDeps") {
      mustRunAfter("getRepos")
      val install = "yarn install"
      commandExecMap.put(
        MultiExecTask.OSType.AUTO, listOf(
          "cd doki-build-source",
          install,
          "yarn build",
          "cd ../masterThemes",
          install,
          "yarn generateAllJetbrains"
        )
      )
    }
    project.tasks.register<MultiExecTask>("getRepos") {
      description = "Retrieves all repositories doki-theme-jetbrains relies on."
      val command = "getRepoDependencies.sh"
      commandExecMap.put(MultiExecTask.OSType.AUTO,listOf(command))
    }
    project.tasks.register<DefaultTask>("initDokiProject") {
      group = "doki"
      description = "Gets all necessary repos and build their dependencies."
      dependsOn("getRepositories","buildThemeDeps")
    }
    project.tasks.register<TemplateVariantBuilder>("genIslandsTemplates") {
      description = "Generates islands templates of each doki theme using darcula templates as the base."
      variantName.set("islands")
      darkParentTheme?.set("Islands Dark")
      lightParentTheme?.set("Islands Light")
    }
  }
}