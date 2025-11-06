package io.unthrottled.doki.legacy

import com.intellij.openapi.project.Project
import io.unthrottled.doki.config.ThemeConfig
import io.unthrottled.doki.settings.actors.ThemeActor.setDokiTheme
import io.unthrottled.doki.themes.ThemeManager
import io.unthrottled.doki.util.toOptional

object LegacyMigration {
  fun migrateIfNecessary() {
  }

  fun newVersionMigration(project: Project) {
    handleThemeRenames()
    migrateMaterialIcons(project)
  }

  private fun migrateMaterialIcons(project: Project) {
    if (
      ThemeConfig.instance.isMaterialFiles ||
      ThemeConfig.instance.isMaterialDirectories ||
      ThemeConfig.instance.isMaterialPSIIcons
    ) {
      ThemeConfig.instance.isMaterialFiles = false
      ThemeConfig.instance.isMaterialDirectories = false
      ThemeConfig.instance.isMaterialPSIIcons = false
    }
  }


  private val renamedThemes =
    setOf(
      // Zero Two Light
      "4fd5cb34-d36e-4a3c-8639-052b19b26ba1",
      // Zero Two Dark
      "8c99ec4b-fda0-4ab7-95ad-a6bf80c3924b",
      // Rimiru -> Rimuru
      "5ca2846d-31a9-40b3-8908-965dad3c127d",
    )

  private fun handleThemeRenames() {
    ThemeManager.instance.currentTheme
      .filter {
        renamedThemes.contains(it.id)
      }
      .ifPresent { renamedTheme ->
        setDokiTheme(
          ThemeManager.instance.allThemes.first {
            renamedTheme.id != it.id
          }.toOptional(),
        )
        setDokiTheme(renamedTheme.toOptional())
      }
  }
}
