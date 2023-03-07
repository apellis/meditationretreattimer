package com.ape.meditationretreattimer.data

import androidx.room.*
import com.ape.meditationretreattimer.model.Timer

@Dao
interface TimerDao {
    @Query("SELECT * FROM timer ORDER BY id ASC")
    fun getAll(): List<Timer>

    @Query("SELECT * FROM timer WHERE id = :id")
    fun getById(id: Int): List<Timer>

    @Insert
    fun insert(timer: Timer)

    @Update
    fun update(timer: Timer)

    @Delete
    fun delete(timer: Timer)
}