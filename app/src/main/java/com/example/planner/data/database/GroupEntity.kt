package com.example.planner.data.database

import androidx.room.Entity

@Entity(tableName = "groups")
data class GroupEntity(
    val id: Int,
    var name: String,
    var picture: Int,
    var color: String
) {

}