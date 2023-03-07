package com.ape.meditationretreattimer.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

enum class SettingName(val settingName: String) {
    USE_24_HOUR_TIME("use24HourTime"),
    NEVER_ASK_FOR_DND_PERMISSION("neverAskForDndPermission"),
    AUTO_SET_DND("autoSetDnd"),
    NEVER_ASK_FOR_AIRPLANE_PERMISSION("neverAskForAirplanePermission"),
    AUTO_SET_AIRPLANE("autoSetAirplane"),
    SHOW_ON_LOCK_SCREEN("showOnLockScreen")
}

@Entity
data class Setting(
    @PrimaryKey @ColumnInfo(name = "key") val key: String,
    @ColumnInfo(name = "value") val value: String) {

    companion object {
        val VALID_SETTINGS: Map<String, List<String>> = mapOf(
            SettingName.USE_24_HOUR_TIME.name to listOf("true", "false"),
            SettingName.NEVER_ASK_FOR_DND_PERMISSION.name to listOf("true", "false"),
            SettingName.AUTO_SET_DND.name to listOf("true", "false"),
            SettingName.NEVER_ASK_FOR_AIRPLANE_PERMISSION.name to listOf("true", "false"),
            SettingName.AUTO_SET_AIRPLANE.name to listOf("true", "false"),
            SettingName.SHOW_ON_LOCK_SCREEN.name to listOf("true", "false"),
        )

        val DEFAULT_SETTINGS: Map<String, String> = mapOf(
            SettingName.USE_24_HOUR_TIME.name to "true",
            SettingName.NEVER_ASK_FOR_DND_PERMISSION.name to "false",
            SettingName.AUTO_SET_DND.name to "true",
            SettingName.NEVER_ASK_FOR_AIRPLANE_PERMISSION.name to "false",
            SettingName.AUTO_SET_AIRPLANE.name to "true",
            SettingName.SHOW_ON_LOCK_SCREEN.name to "true"
        )
    }
}
