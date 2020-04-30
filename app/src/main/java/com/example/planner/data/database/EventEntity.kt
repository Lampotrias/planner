package com.example.planner.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    private var _id: Long,

    @ColumnInfo(name = "name")
    private var _name: String,

    @ColumnInfo(name = "time")
    private val _time: Long,

    @ColumnInfo(name = "time_zone")
    private var timeZone: Int,

    @ColumnInfo(name = "priority_id")
    private var _priority_id: Int,

    @ColumnInfo(name = "alarm_id")
    private val _alarm_id: Int,

    @ColumnInfo(name = "repeat_id")
    private val _repeat_id: Int
)