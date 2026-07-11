package io.unthrottled.doki.settings

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.util.NlsContexts
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.LabelPosition
import com.intellij.ui.dsl.builder.bindSelected
import com.intellij.ui.dsl.builder.bindValue
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.builder.selected
import org.jetbrains.annotations.NonNls
import javax.swing.JComponent


class ThemeSettingsView(private val model: ThemeSettingsModel = ThemeSettings.createThemeSettingsModel()) :
  SearchableConfigurable, Configurable.NoScroll, DumbAware {
  private lateinit var discreetCheckBox: Cell<JBCheckBox>
  override fun getDisplayName(): @NlsContexts.ConfigurableName String = ThemeSettings.THEME_SETTINGS_DISPLAY_NAME
  override fun getId(): @NonNls String = ThemeSettings.SETTINGS_ID

  private fun createGeneralPanel(): DialogPanel {
    return panel {
      row {
        discreetCheckBox = checkBox("Discreet mode").bindSelected(model::discreetMode)
        checkBox("Theme name in statusbar")
          .bindSelected(model::showThemeStatusBar)
          .enabledIf(discreetCheckBox.selected)
      }
      groupRowsRange {
        group("Background Images") {
          row {
            checkBox("Background wallpaper").bindSelected(model::isDokiBackground)
              .comment("Wallpaper will remain after removing plugin, if left enabled!")
            checkBox("Empty editor background").bindSelected(model::isEmptyFrameBackground)
          }
        }
        group("Notifications") {
          row {
            slider(0, 100, 1, 10).label("Opacity:", LabelPosition.TOP).resizableColumn()
              .bindValue(model::notificationOpacity)
            checkBox("Make transparent").bindSelected(model::isSeeThroughNotifications)
          }
        }
      }.enabledIf(discreetCheckBox.selected)
    }
  }

  private fun createStickerPanel(): DialogPanel {
    return panel {
      groupRowsRange {
        group("General") {
          row {
            checkBox("Show sticker").bindSelected(model::areStickersEnabled)
            checkBox("Ignore scaling").bindSelected(model::ignoreScaling)
          }
        }
        group("Position") {
          row {
            comment("Double-click stickers to save current position.")
          }
          row {
            checkBox("Allow positioning").bindSelected(model::isMoveableStickers)
          }
          row {
            button("Reset Position") {
              ThemeSettings.resetStickerPosition()
            }
          }
        }
        group("Sticker Image") {
          row {
            checkBox("Use custom sticker").bindSelected(model::isCustomSticker)
            TODO("Implement textbox with file chooser capabilities for stickers")
            textFieldWithBrowseButton {  }
          }
        }
      }.enabledIf(discreetCheckBox.selected)
    }
  }

  private fun createFontPanel(): DialogPanel {
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