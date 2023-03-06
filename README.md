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
      * Code quality
      * Refactor: Use `ViewModel`'s
         * Move DB queries off main thread and remove `.allowMainThreadQueries()` from the database builder in `data.AppDatabase`
         * Add tests
      * Features
         * Some sort of good UI for editing bell times without having to delete and re-add them
         * Unnamed bells that don't break up a segment (don't change the displayed "Now" segment upon ringing)
         * More options for bell sounds
         * Automatically set up DND mode, silence everything (notifications, other apps' sounds and vibrations)
         * Auto set and unset airplane mode
         * User setting: 12 vs. 24 hour time
         * User setting: Yes/no/never ask again for auto-set DND while playing a timer
         * User setting: Yes/no/never ask again for auto-set airplane mode while playing a timer
         * User setting: Show current segment and time remaining on lock screen

## Planned

* iOS app
   * Status
      * Not started
