package io.unthrottled.doki.settings.actors

import io.unthrottled.doki.config.ThemeConfig
import io.unthrottled.doki.stickers.StickerPaneService

object MoveableStickerActor {
  fun moveableStickers(enabled: Boolean) {
    if (enabled != ThemeConfig.instance.isMoveableStickers) {
      ThemeConfig.instance.isMoveableStickers = enabled
      StickerPaneService.instance.setStickerPositioning(enabled)
    }
  }
}
