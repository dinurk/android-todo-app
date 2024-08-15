package com.example.myapplication1.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication1.data.db.entities.TaskEntity

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAll(): List<TaskEntity>

    @Insert
    fun insertAll(vararg tasks: TaskEntity)

    @Query("DELETE FROM tasks WHERE uuid IN (:uuids)")
    fun deleteAll(uuids: List<String>)
}