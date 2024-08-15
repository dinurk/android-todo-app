package com.example.myapplication1.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey val uuid: String,
    @ColumnInfo(name = "text") val text: String,
)