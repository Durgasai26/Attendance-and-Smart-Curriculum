package com.example.attendencescanner.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "curriculum_activities")
data class CurriculumActivity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val subject: String,
    val description: String,
    val assignedTo: String, // could be student ID or class name
    val dueDate: Long, //date
    val status: String = "Pending", // Pending / Completed
    val score: Int? = null,
    val teacherRemarks: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
