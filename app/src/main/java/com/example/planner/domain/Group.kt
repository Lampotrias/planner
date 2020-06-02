package com.example.planner.domain

import com.example.planner.data.database.GroupEntity

data class Group(
    val id: Long,
    var name: String,
    var picture: Int,
    var color: String,
    var sort: Int,
    var default: Boolean
) {
    fun toGroupEntity() = GroupEntity(id, name, picture, color, sort, default)

}