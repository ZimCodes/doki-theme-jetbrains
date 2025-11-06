package io.unthrottled.doki.settings.actors

import io.unthrottled.doki.config.ThemeConfig
import io.unthrottled.doki.stickers.EditorBackgroundWallpaperService

object BackgroundActor {
  fun handleBackgroundUpdate(enabled: Boolean) {
    if (enabled != ThemeConfig.instance.isDokiBackground) {
      ThemeConfig.instance.isDokiBackground = enabled
      if (enabled) {
        EditorBackgroundWallpaperService.instance.enableEditorBackground()
      } else {
        EditorBackgroundWallpaperService.instance.removeEditorBackground()
      }
    }
  }
}
