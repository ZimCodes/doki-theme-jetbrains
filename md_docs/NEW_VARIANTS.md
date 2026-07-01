# New Variant

**NOTE:** There is currently no support for auto-generating variant editor schemes (`.xml`)

## New LAF variant

To create a new LAF variant, you must create a base template located `doki-build-plugin/assets/templates`.
Then simply run the task `./gradlew genVariantTemplates -Pvariant=<variant-name>`

## Requirements

- File Name format: `base.<variant-name>.laf.template.json`
- JSON format:
  - `type`: "LAF"
  - `name`: "base <variant-name>"
  - `ui`:
    `{ themeMetadata keys: color names (defined in 'masterThemes/definitions/<doki-theme>/master.definition.json' or 'colors key')}`
  - `icons`: **(Optional)** same as `ui` except focused on icon themeMetadata
  - `colors`: **(Optional/Not Recommended)** define new color names in place of hexadecimals. Ex: `accentColor: "#ff3165"`
    I recommend not using this. Use existing `colors` names defined in a doki's `master.definitions.json` file located in
    `masterThemes/` directory instead.


The data model for this JSON format is called `AssetTemplateDefinition` located in `Models.kt` of `doki-build-source-jvm/` directory

You can also use `base.laf.template.json` located at `doki-build-plugin/assets/templates`, as an example to build a new template.

**Example**

```json
{
  "type": "LAF",
  "name": "base islands",
  "ui": {
    "ToolWindow.background": "headerColor",
    "ToolWindow.Header.background": "headerColor",
    "ToolWindow.Header.inactiveBackground": "headerColor",
    "MainWindow.background": "baseBackground",
    "Island.borderColor": "headerColor",
    "Island.arc": 22,
    "Island.borderWidth": 5,
    "EditorTabs.underlinedBorderColor": "accentColor",
    "EditorTabs.inactiveUnderlinedTabBorderColor": "accentColorLessTransparent",
    "EditorTabs.inactiveUnderlinedTabBackground": "highlightColor",
    "EditorTabs.background": "headerColor"
  },
  "icons": {
    "ColorPalette": {
      "Actions.Green": "accentColor",
      "Actions.Blue": "accentColor",
      "Object.Purple": "awesomePurple"
    }
  },
  "colors": {
    "awesomePurple": "#d506ff"
  }
}

```