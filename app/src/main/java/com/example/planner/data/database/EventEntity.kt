package com.example.planner.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.planner.domain.Event

@Entity(
    tableName = "events", foreignKeys = [ForeignKey(
        entity = GroupEntity::class,
        parentColumns = ["_id"],
        childColumns = ["group_id"], onDelete = ForeignKey.CASCADE
    )]
)
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "time")
    var time: Long,

    @ColumnInfo(name = "all_day")
    var allDay: Int,

    @ColumnInfo(name = "zone_offset")
    var zoneOffset: Int,

    @ColumnInfo(name = "group_id")

    var groupId: Long

    /*
    @ColumnInfo(name = "priority_id")
    private var _priority_id: Int,

    @ColumnInfo(name = "alarm_id")
    private val _alarm_id: Int,

    @ColumnInfo(name = "repeat_id")
    private val _repeat_id: Int*/
) {
    fun toEvent() = Event(id, name, time, allDay, zoneOffset, groupId)
}
