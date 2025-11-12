tasks.register<BuildThemes>("buildThemes") {
  buildSourceAssetDirectory.set(project.layout.projectDirectory.dir("buildSrc/assets"))
  masterThemesDirectory.set(project.layout.projectDirectory.dir("masterThemes"))
  rootResourcePath.set(project.layout.projectDirectory.dir("src/main/resources"))
  resMasterThemeSchema.set(rootResourcePath.file("theme-schema/master.theme.schema.json"))
  resDokiThemesDirectory.set(rootResourcePath.dir("doki/themes"))
  resPluginXML.set(rootResourcePath.file("META-INF/plugin.xml"))
}
tasks.register<PatchHTML>("patchHTML") {
  htmlDirectory.set(project.layout.projectDirectory.dir("build/html"))
}