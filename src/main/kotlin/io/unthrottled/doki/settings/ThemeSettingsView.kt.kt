package io.unthrottled.doki.settings

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.util.NlsContexts
import com.intellij.ui.dsl.builder.LabelPosition
import com.intellij.ui.dsl.builder.bindSelected
import com.intellij.ui.dsl.builder.bindValue
import com.intellij.ui.dsl.builder.panel
import org.jetbrains.annotations.NonNls
import javax.swing.JComponent


class ThemeSettingsView(private val model: ThemeSettingsModel = ThemeSettings.createThemeSettingsModel()) :
  SearchableConfigurable, Configurable.NoScroll, DumbAware {
  override fun getDisplayName(): @NlsContexts.ConfigurableName String = ThemeSettings.THEME_SETTINGS_DISPLAY_NAME
  override fun getId(): @NonNls String = ThemeSettings.SETTINGS_ID

  private fun createGeneralPanel(): DialogPanel {
    return panel {
      row {
        checkBox("Discreet mode").bindSelected(model::discreetMode)
        checkBox("Theme name in statusbar").bindSelected(model::showThemeStatusBar)
      }
      group("Background Images") {
        row {
          checkBox("Background wallpaper").bindSelected(model::isDokiBackground)
            .comment("Wallpaper will remain after removing plugin, if left enabled!")
          checkBox("Empty editor background").bindSelected(model::isEmptyFrameBackground)
        }
      }
      group("Notifications") {
        row {
          slider(0, 100, 1, 10).label("Opacity:", LabelPosition.TOP).resizableColumn().bindValue(model::notificationOpacity)
          checkBox("Make transparent").bindSelected(model::isSeeThroughNotifications)
        }
      }
    }
  }
  private fun createStickerPanel(): DialogPanel {
    TODO("Implement Sticker Panel")
  }
  private fun createFontPanel(): DialogPanel{
    TODO("Implement font panel")
  }
  private fun createTabs(): JComponent {
    TODO("Use JTabbedPane to add tabs.")
  }

  override fun createComponent(): JComponent? {
    TODO("Not yet implemented")
  }

  override fun isModified(): Boolean {
    TODO("Not yet implemented")
  }

  override fun apply() {
    TODO("Not yet implemented")
  }


}