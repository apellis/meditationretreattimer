package com.ape.meditationretreattimer

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Utils {
    companion object {
        fun formatLocalTime(lt: LocalTime): String {
            return lt.format(DateTimeFormatter.ofPattern("HH:mm"))
        }

        const val TIME_RESOLUTION_MILLIS: Long = 100
    }

    fun playSound(context: Context, mediaPlayer: MediaPlayer, @RawRes rawResId: Int) {
        // If playSound() only supports one sound at a time. If it is called while a previous
        // call's sound is playing, the previous sound terminates early and the new sound begins
        // playing.
        val assetFileDescriptor = context.resources.openRawResourceFd(rawResId)
        mediaPlayer.run {
            reset()
            setDataSource(
                assetFileDescriptor.fileDescriptor,
                assetFileDescriptor.startOffset,
                assetFileDescriptor.declaredLength)
            prepareAsync()
        }
        assetFileDescriptor.close()
    }
}