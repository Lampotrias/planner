package com.lamp.planner.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lamp.planner.domain.Group

@Entity(tableName = "groups")
data class GroupEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val id: Long,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "picture_id")
    var picture: Int,
    @ColumnInfo(name = "color")
    var color: String,
    @ColumnInfo(name = "sort")
    var sort: Int,
    @ColumnInfo(name = "is_default")
    var isDefault: Boolean
) {
    fun toEvent(): Group = Group(id, name, picture, color, sort, isDefault)

}
