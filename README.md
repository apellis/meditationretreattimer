# Meditation Retreat Timer

A timer for meditation retreats. Open source so you can build and run it yourself for free. Or support the [author](https://twitter.com/a_p_ellis) by purchasing the app on your device's app store for a nominal fee.

## In progress

* Android app
   * Status
      * Current build works with improvements planned as described below
      * Not yet on Play Store
   * TODO
      * Error handling
         * Do proper, not lazy, null safety throughout
         * Input validation for bell times
         * In settings, gray out invalid settings states
      * Code quality
         * Refactor: Use `ViewModel`'s
         * Move DB queries off main thread and remove `.allowMainThreadQueries()` from the database builder in `data.AppDatabase`
         * Add tests
      * Features
         * [P1] Edit name of timer
         * [P1] User setting: Show current segment and time remaining on lock screen
         * [P1] Auto set and unset airplane mode
         * [P1] User setting: Yes/no/never ask again for auto-set airplane mode while playing a timer
         * [P2] More options for bell sounds
         * [P2] Unnamed bells that don't break up a segment (don't change the displayed "Now" segment upon ringing)

## Planned

* iOS app
   * Status
      * Not started
