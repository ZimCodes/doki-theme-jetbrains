## New Color Scheme

> Do not like the color scheme of an existing doki theme?
> Want to make your own color variant? This is the place!

In order to submit a color scheme, you must provide a proof of concept. Follow the steps below to achieve this.
If you haven't done already, [setup your development environment](./md_docs/DEV_SETUP.md).

### Customizing Colors

`masterThemes/definitions` harbors all color definitions for a doki theme.

1. **Navigate** to `masterThemes/definitions`.
2. **Create a copy** of the doki theme, `*.master.definition.json`, you would like to modify in the same folder.
3. **Rename** the file according to the following rules:

- File name  _must_ end with `master.definition.json`.
- `.` must be used in place of spaces. Ex: `kiki.takagi.cool.theme.master.definition.json`
- File name must not be the same name as the original

4. Now open the file and change the folowing values:

- `name`: The name of you doki theme variant
- `displayName`: The name shown to users
- `author`: Your name
- `colors`: This is where you customize your doki theme colors. Just replace each `#hexadecimal` color with your own!
  - You can use `doki-build-plugin/assets/template/base.laf.template.json` as your guide to see what each color name
    control.

### Working with Templates

The next step is to set up a doki template. Luckily, we have a Gradle task.

**Run Gradle task**, `genCustomDokiColorTemplate` to automatically setup a `darcula` template. If you would like to
generate another variant such as, `islands`, specify it through this option, `-Pvariant=<variant-name>`.

**Example:**

```bash
# Generates islands templates for custom doki theme
./gradlew genCustomDokiColorTemplate -Pvariant=islands

# Generates darcula templates for custom doki theme
./gradlew genCustomDokiColorTemplate
```

Along with setting up templates, this task also moves your custom doki theme color definition file to
`masterThemes/jetbrains`.

### Build Themes
