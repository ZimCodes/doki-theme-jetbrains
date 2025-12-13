package io.unthrottled.doki.build.plugin

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.Exec
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction

abstract class MultiExecTask : Exec() {

  init {
    group = "doki"
    description = "Executes a series of commands in the command line"
  }

  @get:Input
  abstract val commandExecList: ListProperty<String>

  @get:InputDirectory
  abstract val startDirectory: DirectoryProperty

  @TaskAction
  override fun exec() {
    val startDir = startDirectory.get().asFile
    workingDir(startDir)
    val commands: List<String> = commandExecList.get()
    val joinedCmds: String = commands.joinToString(" && ")
    val osName = System.getProperty("os.name").lowercase()
    if (osName.contains("windows")) {
      commandLine("cmd", "/c", joinedCmds)
    } else {
      commandLine("sh", "-c", "'$joinedCmds'")
    }
    super.exec()
  }

}