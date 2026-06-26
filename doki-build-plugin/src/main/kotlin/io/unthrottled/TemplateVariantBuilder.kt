package io.unthrottled.doki.build.plugin

import io.unthrottled.doki.build.jvm.models.DokiThemeTemplate
import io.unthrottled.doki.build.jvm.tools.CommonConstructionFunctions
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.util.regex.Pattern
import kotlin.streams.asSequence

abstract class TemplateVariantBuilder : DefaultTask() {

  init {
    // Runs this task every time. No skipping!
    outputs.upToDateWhen { false }
    dokiThemeDirectory.convention(project.layout.projectDirectory.dir("doki-build-plugin/assets/themes"))
  }

  @get:InputDirectory
  @get:PathSensitive(PathSensitivity.RELATIVE)
  abstract val dokiThemeDirectory: DirectoryProperty

  @get:Input
  abstract val jsonItems: MapProperty<String, Any>

  @get:Input
  abstract val variantName: Property<String>

  fun jsonTemplates(): List<DokiThemeTemplate> {
    val templatePath = dokiThemeDirectory.asFile.get().toPath()
    val jsonTemps = baseJSONTemplates(templatePath, variantName.get())
    val variantJSONTemps = renamePathToVariant(jsonTemps)
    val modifiedVariantTemps = addItemsToTemplate(variantJSONTemps)
    writeToJSON(modifiedVariantTemps)
  }

  fun addItemsToTemplate(jsonVariantTemplates: Map<Path, DokiThemeTemplate>): Map<Path, DokiThemeTemplate> =
    jsonVariantTemplates.map { (path, template) ->
      path to DokiThemeTemplate(
        id = template.id + variantName.get(),
        parentTheme = jsonItems.get()["parentTheme"] as? String? ?: template.parentTheme,
        editorScheme = template.editorScheme,
        overrides = template.overrides,
        ui = template.ui,
        uiBase = getUIBase(path, template.uiBase)
      )
    }.toMap()

  fun getUIBase(path: Path, templateUIBase: String?): String {
    // TODO: return the islands version of the uibase.
  }

  fun writeToJSON(jsonVariantTemplates: Map<Path, DokiThemeTemplate>) {
    jsonVariantTemplates.forEach { path, template ->
      Files.newBufferedWriter(
        path, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING,
        StandardOpenOption.CREATE
      ).use { writer ->
        CommonConstructionFunctions.gson.toJson(template, writer)
      }
    }
  }

  fun renamePathToVariant(dokiTemplate: Map<Path, DokiThemeTemplate>): Map<Path, DokiThemeTemplate> =
    dokiTemplate.mapKeys { (path, _) ->
      val fileName = path.fileName.toString()
      val separator = Pattern.compile("[.]")
      val nameSplit = fileName.split(separator)
      val startSplit = nameSplit.takeWhile { it != "jetbrains" }
      val endSplit = nameSplit.takeLast(3)
      val nameVariantSplit = startSplit + variantName.get() + endSplit
      val variantFileName = nameVariantSplit.joinToString(".")
      path.resolveSibling(variantFileName)
    }

  /*
  * Gets the JSON format of each doki theme darcula template
  * */
  fun baseJSONTemplates(templatePath: Path, variantName: String): Map<Path, DokiThemeTemplate> =
    Files.walk(templatePath).filter {
      Files.isRegularFile(it) && !it.fileName.toString().contains(variantName)
    }.asSequence().associateWith { path ->
      Files.newBufferedReader(path).use { reader ->
        CommonConstructionFunctions.gson.fromJson(reader, DokiThemeTemplate::class.java)
      }
    }
}
