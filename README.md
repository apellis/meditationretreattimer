# Meditation Retreat timer

A timer for meditation retreats. Open source so you can build and run it yourself for free. Or support the [author](https://twitter.com/a_p_ellis) by purchasing the app on your device's app store for a nominal fee.

## In progress

* Android app
   * Status
      * Current build basically works but is rough around the edges
   * TODO
      * Bugfixes
         * Stop current segment name from flickering (boldface?) in play timer UI
      * Error handling
         * Many null checks
         * Input validation for bell times
      * Code quality
         * Move DB queries off main thread and remove `.allowMainThreadQueries()` from the database builder in `data.AppDatabase`
      * Features
         * Some sort of good UI for editing bell times without having to delete and re-add them
         * Unnamed bells that don't break up a segment (don't change the displayed "Now" segment upon ringing)
         * More options for bell sounds
         * Automatically set up DND mode, silence everything (notifications, other apps' sounds and vibrations)
      * QA
         * Rigorously check for failure modes around device sleep, app deactivation, manual screen lock, etc. -- if in `PlayTimerActivity`, bells should always sound, even when the screen is manually locked

## Planned

* iOS app
   * Status
      * Not started
