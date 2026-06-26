package io.unthrottled.doki.build.plugin

import io.unthrottled.doki.build.jvm.models.DokiThemeTemplate
import io.unthrottled.doki.build.jvm.tools.CommonConstructionFunctions
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.util.regex.Pattern
import kotlin.streams.asSequence

abstract class TemplateVariantBuilder : DefaultTask() {
  init {
    group = "doki"
    // Runs this task every time.
    outputs.upToDateWhen { false }
    dokiThemeDirectory.convention(project.layout.projectDirectory.dir("doki-build-plugin/assets/themes"))
  }

  @get:InputDirectory
  @get:PathSensitive(PathSensitivity.RELATIVE)
  abstract val dokiThemeDirectory: DirectoryProperty

  @get:Input
  abstract val darkParentTheme: Property<String>?

  @get:Input
  abstract val lightParentTheme: Property<String>?

  @get:Input
  abstract val variantName: Property<String>

  @TaskAction
  fun run() {
    val templatePath = dokiThemeDirectory.asFile.get().toPath()
    val jsonTemps = baseJSONTemplates(templatePath)
    val variantJSONTemps = renamePathToVariant(jsonTemps)
    val modifiedVariantTemps = updateTemplates(variantJSONTemps)
    writeToJSON(modifiedVariantTemps)
  }

  /*
  * Updates the JSON templates with variant specific details.
  * */
  fun updateTemplates(jsonVariantTemplates: Map<Path, DokiThemeTemplate>): Map<Path, DokiThemeTemplate> =
    jsonVariantTemplates.map { (path, template) ->
      val isDark = path.fileName.toString().contains("dark")
      path to DokiThemeTemplate(
        id = template.id + variantName.get(),
        parentTheme = getParentTheme(isDark),
        editorScheme = template.editorScheme,
        overrides = template.overrides,
        ui = template.ui,
        uiBase = getUIBase(template.uiBase, isDark)
      )
    }.toMap()

  /*
  * Gets the correct parentTheme based on light/dark theme type.
  * */
  fun getParentTheme(isDark: Boolean): String? {
    return if (isDark) darkParentTheme?.get() else lightParentTheme?.get()
  }

  /*
  * Gets the uibase specifically designed for the variant.
  * */
  fun getUIBase(templateUIBase: String?, isDark: Boolean): String =
    if (templateUIBase == null) uibaseFromName(isDark) else "$templateUIBase ${variantName.get()}"

  /*
  * Gets the uibase value from the file name.
  * */
  fun uibaseFromName(isDark: Boolean): String =
    if (isDark) "dark ${variantName.get()}" else "light ${variantName.get()}"

  fun writeToJSON(jsonVariantTemplates: Map<Path, DokiThemeTemplate>) {
    jsonVariantTemplates.forEach { (path, template) ->
      Files.newBufferedWriter(
        path, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING,
        StandardOpenOption.CREATE
      ).use { writer ->
        CommonConstructionFunctions.gson.toJson(template, writer)
      }
    }
  }
/*
* Rename file names to include the name of the variant.
*
* Example: my.theme.dark.<variant-name>.jetbrains.definition.jsono
* */
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
  fun baseJSONTemplates(templatePath: Path): Map<Path, DokiThemeTemplate> =
    Files.walk(templatePath).filter {
      val fileName = it.fileName.toString()
      Files.isRegularFile(it) && fileName.endsWith("json") && !fileName.contains(variantName.get())
    }.asSequence().associateWith { path ->
      Files.newBufferedReader(path).use { reader ->
        CommonConstructionFunctions.gson.fromJson(reader, DokiThemeTemplate::class.java)
      }
    }
}
