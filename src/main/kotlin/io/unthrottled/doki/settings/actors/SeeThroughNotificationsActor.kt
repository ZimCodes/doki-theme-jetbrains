package io.unthrottled.doki.settings.actors

import io.unthrottled.doki.config.ThemeConfig
import io.unthrottled.doki.service.GlassNotificationService

object SeeThroughNotificationsActor {
  fun enableSeeThroughNotifications(
    enabled: Boolean,
    notificationOpacity: Int,
  ) {
    ThemeConfig.instance.isSeeThroughNotifications = enabled
    ThemeConfig.instance.notificationOpacity = notificationOpacity
    GlassNotificationService.makeNotificationSeeThrough()
  }
}
