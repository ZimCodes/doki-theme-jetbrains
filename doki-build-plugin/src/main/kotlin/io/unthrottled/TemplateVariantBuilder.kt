package io.unthrottled.doki.build.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.CacheableTask

@CacheableTask
abstract class TemplateVariantBuilder: DefaultTask() {

}
