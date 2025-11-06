package io.unthrottled.doki.activity

import com.intellij.notification.Notification
import com.intellij.notification.NotificationAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.intellij.openapi.updateSettings.impl.pluginsAdvertisement.installAndEnable
import io.unthrottled.doki.promotions.MessageBundle
import io.unthrottled.doki.service.DOKI_ICONS_PLUGIN_ID

class LegacyMigrateProjectActivity : ProjectActivity {
  override suspend fun execute(project: Project) {
    val installAction =
        object : NotificationAction(MessageBundle.message("promotion.action.ok")) {
          override fun actionPerformed(
            e: AnActionEvent,
            notification: Notification,
          ) {
            installAndEnable(
              project,
              setOf(
                PluginId.getId(DOKI_ICONS_PLUGIN_ID),
              ),
            ) {
            }
            notification.expire()
          }
        }

  }

}