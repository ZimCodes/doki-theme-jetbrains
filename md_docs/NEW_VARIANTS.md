# New Variant

**NOTE:** There is currently no support for generating variant editor schemes (`.xml`)

## New LAF variant

To create a new LAF variant, you must create a base template located `doki-build-plugin/assets/templates`.
Then simply run the task `./gradlew genVariantTemplates -Pvariant=<variant-name>`

## Requirements

- File Name format: `base.<variant-name>.laf.template.json`
- JSON format:
  - `type`: "LAF"
  - `name`: "base <variant-name>"
  - `ui`: `{ themeMetadata keys: value}`

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
  }
}

```