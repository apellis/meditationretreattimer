# Impermanence

A timer for meditation retreats. Open source and freely available on the iOS App Store.

## iOS

### Status

* Version 1.1 available [here](https://apps.apple.com/us/app/impermanence/id6462615517)
* More polished than the Android version; I'll likely remake the Android version to mirror the iOS version

### TODO

* Better editing functionality, e.g. "clone day" or "clone segment"
* Color theming for segments
* More accessibility hints
* Add contextual help in editing UI
* 12- vs. 24-hour time setting
* Customizable bells (sound, number of rings)
* "Quick sit" feature
* Manual bell ring from active day UI

### Known bugs

* On iOS 15, segments cannot be reordered or deleted when editing a day

## Android

### Status

* Version 1.0 available [here](https://play.google.com/store/apps/details?id=com.ape.meditationretreattimer&hl=en&gl=US), under the older name "Meditation Retreat Timer"
* Likely to be replaced by a version more closely following the iOS version's design

### TODO

* Bugfixes
  * DND turns off sounds; add exception for MRT sounds to play anyway
* Error
  * Do proper, not lazy, null safety throughout
  * Input validation for bell times
  * In settings, gray out invalid settings states
* Code quality
  * Move DB queries off main thread and remove `.allowMainThreadQueries()` from the database builder in `data.AppDatabase`
  * Add tests
  * [Bigger] Use `LiveData` and `ViewModel`
  * [Bigger] Migrate to Compose
* Features
  * [P1] Edit name of timer
  * [P1] User setting: Show current segment and time remaining on lock screen
  * [P1] Auto set and unset airplane mode
  * [P1] User setting: Yes/no/never ask again for auto-set airplane mode while playing a timer
  * [P2] More options for bell sounds
  * [P2] Unnamed bells that don't break up a segment (don't change the displayed "Now" segment upon ringing)
