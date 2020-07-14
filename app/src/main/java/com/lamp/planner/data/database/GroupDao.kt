package com.lamp.planner.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface GroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createGroup(groupEntity: GroupEntity): Long

    @Query("SELECT * FROM groups ORDER BY sort DESC")
    fun getGroups(): List<GroupEntity>

    @Query("UPDATE groups SET is_default = 0")
    fun invalidateDefault()

    @Query("UPDATE groups SET is_default = 1 WHERE _id = :id")
    fun setDefaultGroupInternal(id: Long): Int

    @Transaction
    fun setDefaultGroup(id: Long): Int {
        invalidateDefault()
        return setDefaultGroupInternal(id)
    }

    @Query("DELETE FROM groups WHERE _id IN (:groupIds)")
    fun deleteGroups(groupIds: List<Long>): Int

    @Query("UPDATE groups SET color = :color WHERE _id = :id")
    fun updateColor(id: Long, color: String): Int

    @Query("UPDATE groups SET picture_id = :picture WHERE _id = :id")
    fun updatePicture(id: Long, picture: Int): Int
}
