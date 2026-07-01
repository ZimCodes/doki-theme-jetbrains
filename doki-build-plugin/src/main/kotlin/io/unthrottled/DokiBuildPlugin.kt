package io.unthrottled.doki.build.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

enum class ThemeVariant {
  DARCULA, ISLANDS;

  val lowercase: String
    get() = this.name.lowercase()
}

class DokiBuildPlugin : Plugin<Project> {
  override fun apply(project: Project) {
    val variant: String = project.findProperty("variant") as String? ?: ThemeVariant.DARCULA.lowercase
    fun isDefaultVariant(): Boolean = !project.hasProperty("variant") || variant == ThemeVariant.DARCULA.lowercase
    project.tasks.register<BuildThemesTask>("buildThemes") {
      variantName.set(variant)
      buildSourceAssetDirectory.set(project.layout.projectDirectory.dir("doki-build-plugin/assets"))
      masterThemesDirectory.set(project.layout.projectDirectory.dir("masterThemes"))
      rootResourcePath.set(project.layout.projectDirectory.dir("src/main/resources"))
      resMasterThemeSchema.set(rootResourcePath.file("theme-schema/master.theme.schema.json"))
      resDokiThemesDirectory.set(rootResourcePath.dir("doki/themes"))
      resPluginXML.set(rootResourcePath.file("META-INF/plugin.xml"))
      templatePluginXML.set(buildSourceAssetDirectory.file("plugin.xml"))
    }
    // NOTE: To generate a variant: gradlew genTemplates -Pvariant=islands
    project.tasks.register<ThemeVariantBuilder>("genVariantTemplates") {
      if (isDefaultVariant()) {
        throw IllegalArgumentException("You have must specify a non-darcula variant: '--project-prop=<variant>'")
      }
      dependsOn("genVariantBaseTemplates")
      description = "Generates variant templates of each doki theme using darcula templates as the base."
      variantName.set(variant)
      val capitalName: String = variant.replaceFirstChar { it.titlecaseChar() }
      darkParentTheme?.set("$capitalName Dark")
      lightParentTheme?.set("$capitalName Light")
    }
    // NOTE: To generate a variant: gradlew genVariantBaseTemplates -Pvariant=islands
    project.tasks.register<TemplateVariantBuilder>("genVariantBaseTemplates") {
      if (isDefaultVariant()) {
        throw IllegalArgumentException("You have must specify a non-darcula variant: '--project-prop=<variant>'")
      }
      description = "Generates base starter templates for a variant using darcula's base templates as a guide."
      variantName.set(variant)
    }
    // NOTE: To generate a variant: gradlew genVariantBaseTemplates -Pvariant=islands
    project.tasks.register<MultiExecTask>("genCustomDokiColorTemplate") {
      description =
        "Generates a doki template based on all newly created custom doki color variant found in 'masterThemes/' folder."
      if (!isDefaultVariant()) {
        dependsOn("genVariantTemplates")
      }
      val generateCmd = "yarn generateCustomJetbrainsTemplate${if (variant == null) "" else " $variant"}"
      commandExecMap.put(
        MultiExecTask.OSType.AUTO, listOf(
          "cd masterThemes",
          generateCmd,
        )
      )
    }
    project.tasks.register<DefaultTask>("initDokiProject") {
      group = "doki"
      description = "Gets all necessary repos and build their dependencies."
      dependsOn("buildThemeDeps")
    }
    project.tasks.register<MultiExecTask>("buildThemeDeps") {
      description = "build dependencies found in each doki sub project retrieved from 'getRepo'"
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
      commandExecMap.put(MultiExecTask.OSType.AUTO, listOf(command))
    }
    project.tasks.register<PatchHTMLTask>("patchHTML") {
      htmlDirectory.set(project.layout.projectDirectory.dir("build/html"))
    }
  }
}