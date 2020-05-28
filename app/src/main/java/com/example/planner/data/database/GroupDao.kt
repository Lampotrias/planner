package com.example.planner.data.database

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface GroupDao {
    @Insert
    fun createGroup(groupEntity: GroupEntity): Int
}