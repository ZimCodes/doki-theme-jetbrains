package io.unthrottled.doki.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.FontComboBox
import com.intellij.util.ui.FontInfo
import io.unthrottled.doki.config.THEME_CONFIG_TOPIC
import io.unthrottled.doki.config.ThemeConfig
import io.unthrottled.doki.settings.actors.BackgroundActor
import io.unthrottled.doki.settings.actors.ConsoleFontActor
import io.unthrottled.doki.settings.actors.CustomFontSizeActor
import io.unthrottled.doki.settings.actors.DiscreetModeActor
import io.unthrottled.doki.settings.actors.EmptyFrameBackgroundActor
import io.unthrottled.doki.settings.actors.MoveableStickerActor
import io.unthrottled.doki.settings.actors.StickerActor
import io.unthrottled.doki.settings.actors.SeeThroughNotificationsActor
import io.unthrottled.doki.settings.actors.StickerHideActor
import io.unthrottled.doki.settings.actors.ThemeStatusBarActor
import io.unthrottled.doki.stickers.CustomStickerService
import io.unthrottled.doki.stickers.StickerLevel
import io.unthrottled.doki.stickers.StickerPaneService
import io.unthrottled.doki.themes.ThemeManager
import java.net.URI
import java.util.Vector
import javax.swing.DefaultComboBoxModel

object ThemeSettings {
  const val SETTINGS_ID = "io.unthrottled.doki.settings.ThemeSettings"
  const val THEME_SETTINGS_DISPLAY_NAME = "Doki Theme Settings"
  val CHANGELOG_URI =
    URI("https://github.com/doki-theme/doki-theme-jetbrains/blob/main/changelog/CHANGELOG.md")
  private const val REPOSITORY = "https://github.com/doki-theme/doki-theme-jetbrains"
  const val ULTIMATE_INSTRUCTIONS = "$REPOSITORY/wiki/Ultimate-Theme-Setup"
  val ISSUES_URI = URI("$REPOSITORY/issues")
  val REVIEW_URI = URI("https://plugins.jetbrains.com/plugin/10804-the-doki-theme/reviews")

  @JvmStatic
  fun createThemeSettingsModel(): ThemeSettingsModel =
    ThemeSettingsModel(
      areStickersEnabled = ThemeConfig.getInstance().currentStickerLevel == StickerLevel.ON,
      currentTheme = ThemeManager.getInstance().currentTheme.map { it.name }.orElseGet { ThemeManager.DEFAULT_THEME_NAME },
      showThemeStatusBar = ThemeConfig.getInstance().showThemeStatusBar,
      currentSticker = ThemeConfig.getInstance().currentSticker,
      isNotShowReadmeAtStartup = ThemeConfig.getInstance().isNotShowReadmeAtStartup,
      isMoveableStickers = ThemeConfig.getInstance().isMoveableStickers,
      isDokiBackground = ThemeConfig.getInstance().isDokiBackground,
      discreetMode = ThemeConfig.getInstance().discreetMode,
      isEmptyFrameBackground = ThemeConfig.getInstance().isEmptyFrameBackground,
      isCustomSticker = CustomStickerService.isCustomStickers,
      customStickerPath = CustomStickerService.getCustomStickerPath().orElse(""),
      isCustomFontSize = ThemeConfig.getInstance().isGlobalFontSize,
      customFontSizeValue = ThemeConfig.getInstance().customFontSize,
      isSeeThroughNotifications = ThemeConfig.getInstance().isSeeThroughNotifications,
      notificationOpacity = ThemeConfig.getInstance().notificationOpacity,
      isOverrideConsoleFont = ThemeConfig.getInstance().isOverrideConsoleFont,
      consoleFontValue = ThemeConfig.getInstance().consoleFontName,
      maxStickerHeight = ThemeConfig.getInstance().maxStickerHeight,
      maxStickerWidth = ThemeConfig.getInstance().maxStickerWidth,
      capStickerDimensions = ThemeConfig.getInstance().capStickerDimensions,
      smallMaxStickerHeight = ThemeConfig.getInstance().smallMaxStickerHeight,
      smallMaxStickerWidth = ThemeConfig.getInstance().smallMaxStickerWidth,
      showSmallStickers = ThemeConfig.getInstance().showSmallStickers,
      ignoreScaling = ThemeConfig.getInstance().ignoreScaling,
      hideOnHover = ThemeConfig.getInstance().hideOnHover,
      hideDelayMS = ThemeConfig.getInstance().hideDelayMS,
    )

  fun apply(themeSettingsModel: ThemeSettingsModel) {
    StickerActor.enableStickers(themeSettingsModel.areStickersEnabled, false)
    StickerActor.swapStickers(themeSettingsModel.currentSticker, false)
    StickerActor.ignoreScaling(themeSettingsModel.ignoreScaling)
    StickerActor.setCustomSticker(
      themeSettingsModel.customStickerPath,
      themeSettingsModel.isCustomSticker,
      false,
    )
    StickerActor.setDimensionCapping(
      themeSettingsModel.capStickerDimensions,
      themeSettingsModel.maxStickerWidth,
      themeSettingsModel.maxStickerHeight,
    )
    StickerActor.setSmolStickers(
      themeSettingsModel.showSmallStickers,
      themeSettingsModel.smallMaxStickerWidth,
      themeSettingsModel.smallMaxStickerHeight,
    )
    ThemeStatusBarActor.applyConfig(themeSettingsModel.showThemeStatusBar)
    MoveableStickerActor.moveableStickers(themeSettingsModel.isMoveableStickers)
    BackgroundActor.handleBackgroundUpdate(themeSettingsModel.isDokiBackground)
    EmptyFrameBackgroundActor.handleBackgroundUpdate(themeSettingsModel.isEmptyFrameBackground)
    CustomFontSizeActor.enableCustomFontSize(
      themeSettingsModel.isCustomFontSize,
      themeSettingsModel.customFontSizeValue,
    )
    ConsoleFontActor.enableCustomFontSize(
      themeSettingsModel.isOverrideConsoleFont,
      themeSettingsModel.consoleFontValue,
    )
    SeeThroughNotificationsActor.enableSeeThroughNotifications(
      themeSettingsModel.isSeeThroughNotifications,
      themeSettingsModel.notificationOpacity,
    )
    DiscreetModeActor.enableDiscreetMode(themeSettingsModel.discreetMode)
    StickerHideActor.setStickerHideStuff(themeSettingsModel.hideOnHover, themeSettingsModel.hideDelayMS)
    ApplicationManager.getApplication().messageBus.syncPublisher(
      THEME_CONFIG_TOPIC,
    ).themeConfigUpdated(ThemeConfig.getInstance())
  }

  fun resetStickerPosition() {
    StickerPaneService.getInstance().resetMargins();
  }

  fun createThemeComboBoxModel(settingsSupplier: () -> ThemeSettingsModel): ComboBox<String> {
    val themeComboBox =
      ComboBox(
        DefaultComboBoxModel(
          Vector(
            ThemeManager.getInstance().allThemes
              .sortedBy { theme -> theme.name }
              .map { it.name },
          ),
        ),
      )
    themeComboBox.model.selectedItem = settingsSupplier().currentTheme
    themeComboBox.addActionListener {
      settingsSupplier().currentTheme = themeComboBox.model.selectedItem as String
    }
    return themeComboBox
  }

  fun createConsoleFontComboBoxModel(settingsSupplier: () -> ThemeSettingsModel): FontComboBox {
    val fontComboBox = FontComboBox()
    fontComboBox.addActionListener {
      val fontInfo = fontComboBox.model.selectedItem as? FontInfo
      if (fontInfo != null) {
        settingsSupplier().consoleFontValue = fontInfo.font.name
      }
    }
    return fontComboBox
  }
}
