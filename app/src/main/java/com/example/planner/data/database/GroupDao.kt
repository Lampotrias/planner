package com.example.planner.data.database

import androidx.room.*

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
    fun setDefaultGroup(id: Long): Int{
        invalidateDefault()
        return setDefaultGroupInternal(id)
    }
}