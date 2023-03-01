# Meditation Retreat timer

A timer for meditation retreats. Open source so you can build and run it yourself for free. Or support the [author](https://twitter.com/a_p_ellis) by purchasing the app on your device's app store for a nominal fee.

## In progress

* Android app
   * Status
      * Current build basically works but is rough around the edges
   * TODO
      * Bugfixes
         * (Can't consistently repro) Sometimes there is a spurious bell immediately upon starting to play a timer _in medias res_
      * Error handling
         * Many null checks
         * Input validation for bell times
      * Code quality
         * Move DB queries off main thread and remove `.allowMainThreadQueries()` from the database builder in `data.AppDatabase`
         * Add tests
      * Features
         * Some sort of good UI for editing bell times without having to delete and re-add them
         * Unnamed bells that don't break up a segment (don't change the displayed "Now" segment upon ringing)
         * More options for bell sounds
         * Automatically set up DND mode, silence everything (notifications, other apps' sounds and vibrations)

## Planned

* iOS app
   * Status
      * Not started
