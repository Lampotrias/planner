package com.example.planner.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.planner.domain.Event

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    var _id: Long,

    @ColumnInfo(name = "name")
    var _name: String,

    @ColumnInfo(name = "time")
    var _time: Long,

    @ColumnInfo(name = "zone_offset")
    var _zoneOffset: Int/*,

    @ColumnInfo(name = "priority_id")
    private var _priority_id: Int,

    @ColumnInfo(name = "alarm_id")
    private val _alarm_id: Int,

    @ColumnInfo(name = "repeat_id")
    private val _repeat_id: Int*/
){
    fun toEvent() = Event(_id, _name, _time, _zoneOffset)
}