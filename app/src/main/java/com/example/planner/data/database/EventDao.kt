package com.example.planner.data.database

import androidx.annotation.NonNull
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveEvent(@NonNull eventEntity: EventEntity): Long

    @Query("SELECT * FROM events ORDER BY time ASC")
    fun getAllEvents(): List<EventEntity>

    @Query("SELECT * FROM events WHERE _id = :id")
    fun getEventById(id: Long): EventEntity
}