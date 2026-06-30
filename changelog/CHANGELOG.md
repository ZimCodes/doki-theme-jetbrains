# Changelog
---
## - [Islands Themes]
### Added
- Support islands theme for all doki themes

### Updated
- Improve background color to buttons
- Implement border color for buttons
- Update color for scrollbar in editor's tab section 

### Removed
- Remove deprecated/unsupported themeMetadata keys

### Fixed
- Alert dialog boxes does not follow current doki theme
- Notification link colors not folowing current doki theme
- links pointing to predecessors repo
- Border for inactive tab not following current doki theme

## 2.2.0 - [Build Support 2026.1]

## Updated
- dependencies
- Upgrade Gradle: `9.2.1` -> `9.4.1`

## 2.1.0 - [Build Support 2025.3]

## Added

- IDE build support for 2025.3

## Updated

- Dependencies

### Development

- Updated gradle: `9.2` -> `9.2.1`
- New custom task for building dependencies for themes
- Update local repo dependency sources

## 2.0.1 - [Miku: Visual Lines]

### Changed

- Miku Theme: `Visual Line` color now matches `Hard Wrap` color

### Development

- Change multi-project structure: `buildSrc` -> composite builds _(`build-logic`)_
- Adds build caching support

## 2.0.0 - [Build Support 2025.2]

- Removes error logging
- Removes test implementations
- Removes deprecated items
- Replace deprecated items with modern items
- Remove Promotional materials. _(e.g. AniMemes)_ except suggestive content.
- Decouple _AniMemes_ from this plugin completely
- Reduce overall Notifications introduced by plugin
- Remove New Update notifications by plugin
- Remove 1st time user notifications
- Replace option to toggle discreet mode for **all** promotional plugins. Discreet mode setting will toggle for this
  plugin only.
- Remove option to apply theme change animation
- Removes theme switcher from Doki Settings
- Replace deprecated `Alarms()` in place of Kotlin coroutines
- Removes legacy project migration algorithms and activities
- Update dependencies
- Upgrade gradle from 8.13 to 9.2
- Project is now configuration cache compatible
- Increase max heap and metaspace for Gradle project
- Update README
- Remove workflow files
- Fix [Issue #824](https://github.com/doki-theme/doki-theme-jetbrains/issues/824): Text spacing shrinks when zoom is
  above `100%`
- Fix [Issue #821](https://github.com/doki-theme/doki-theme-jetbrains/issues/821): Left/Right ToolWindow Toolbar not
  following current Doki theme.
- Fix [Issue #819](https://github.com/doki-theme/doki-theme-jetbrains/issues/820): Hatsune Miku theme's Visual Line &
  Hard wrap guides' color matching the editor background, making it hard to see
