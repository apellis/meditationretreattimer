package com.ape.meditationretreattimer.data

import androidx.room.*
import com.ape.meditationretreattimer.model.Setting

@Dao
interface SettingDao {
    // Settings use a "property bag" pattern

    fun getAll(): Map<String, String> {
        return getAllQuery().fold(HashMap()) { map, setting ->
            map[setting.key] = setting.value
            map
        }
    }

    @Query("SELECT key, value FROM Setting ORDER BY key ASC")
    fun getAllQuery(): List<Setting>

    @Query("SELECT value FROM Setting WHERE key = :key")
    fun get(key: String): String

    fun set(key: String, value: String) {
        if (!Setting.VALID_SETTINGS.containsKey(key)) {
            throw IllegalArgumentException("Illegal setting name: $key")
        } else if (!Setting.VALID_SETTINGS[key]!!.contains(value)) {
            throw IllegalArgumentException("Illegal value for setting ${key}: $value")
        }
        upsert(Setting(key, value))
    }

    @Upsert
    fun upsert(setting: Setting)
}